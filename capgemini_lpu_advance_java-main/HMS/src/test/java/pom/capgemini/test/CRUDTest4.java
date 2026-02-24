package pom.capgemini.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Appointment;
import pom.capgemini.entity.Department;
import pom.capgemini.entity.Doctor;
import pom.capgemini.entity.Prescription;
import pom.capgemini.util.PersistenceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 4: Appointment → Prescription (Unidirectional One-to-One)
 *
 * Tests the uni 1:1 relationship where:
 * - Each Appointment may have one Prescription
 * - Prescription has NO reference back to Appointment
 * - Appointment owns the FK (prescription_id)
 * - Navigation is one-way: Appointment → Prescription only
 * - Uses optional=true to allow appointments without prescriptions
 */
public class CRUDTest4 {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        entityManager = PersistenceUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        System.out.println("\n========== TEST 4: Appointment → Prescription (Uni 1:1) ==========\n");
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Step 1: Create Prescription entity.
     * Prove that Prescription has no Appointment reference.
     */
    @Test
    public void testCreatePrescriptionWithoutAppointmentRef() {
        System.out.println("Step 1: Create Prescription without Appointment reference");
        transaction.begin();

        Prescription prescription = new Prescription(
            "Amoxicillin 500mg",
            "1 tablet twice daily for 7 days",
            LocalDate.of(2026, 2, 23)
        );
        prescription.setActive(true);

        entityManager.persist(prescription);
        Long prescId = prescription.getId();
        transaction.commit();

        System.out.println("✓ Prescription created without Appointment reference");
        System.out.println("  Prescription: " + prescription);
        System.out.println("  Note: presc.getAppointment() does NOT exist (unidirectional)");
        assertNotNull(prescId);
    }

    /**
     * Step 2: Create Appointment WITH a Prescription.
     * Use optional=true to demonstrate appointments can have prescriptions.
     */
    @Test
    public void testCreateAppointmentWithPrescription() {
        System.out.println("Step 2: Create Appointment WITH Prescription");
        transaction.begin();

        Department general = new Department("General Medicine", "Floor 1", "Dr. Wilson");
        Doctor doctor = new Doctor("Dr. Wilson", "General Medicine", "GM" + System.nanoTime(), general);

        Appointment appointment = new Appointment(
            LocalDateTime.of(2026, 3, 10, 10, 0),
            "Bacterial infection treatment"
        );
        appointment.setStatus("COMPLETED");

        Prescription prescription = new Prescription(
            "Amoxicillin 500mg + Paracetamol 500mg",
            "Amoxicillin: 1 tablet x 3 times daily for 7 days\nParacetamol: 1 tablet x 2 times daily as needed",
            LocalDate.of(2026, 3, 10)
        );

        // Link appointment to prescription
        appointment.setPrescription(prescription);

        doctor.getAppointments().add(appointment);
        entityManager.persist(general);
        entityManager.persist(doctor);
        entityManager.persist(appointment);
        entityManager.persist(prescription);
        entityManager.flush();
        Long apptId = appointment.getId();
        Long prescId = prescription.getId();
        transaction.commit();

        System.out.println("✓ Appointment created WITH Prescription");
        System.out.println("  Appointment: " + appointment);
        System.out.println("  Prescription: " + prescription);
        assertNotNull(apptId);
        assertNotNull(prescId);
    }

    /**
     * Step 3: Create Appointment WITHOUT a Prescription.
     * Demonstrate optional=true allows NULL prescription_id.
     */
    @Test
    public void testCreateAppointmentWithoutPrescription() {
        System.out.println("Step 3: Create Appointment WITHOUT Prescription (optional=true)");
        transaction.begin();

        Department preventive = new Department("Preventive Care", "Floor 2", "Dr. Davis");
        Doctor doctor = new Doctor("Dr. Davis", "Preventive Medicine", "PM" + System.nanoTime(), preventive);

        Appointment appointment = new Appointment(
            LocalDateTime.of(2026, 3, 15, 2, 0),
            "Annual checkup - routine"
        );
        appointment.setStatus("SCHEDULED");
        // NO prescription set - prescription_id will be NULL

        doctor.getAppointments().add(appointment);
        entityManager.persist(preventive);
        entityManager.persist(doctor);
        entityManager.persist(appointment);
        entityManager.flush();
        Long apptId = appointment.getId();
        transaction.commit();

        entityManager.clear();
        Appointment loadedAppt = entityManager.find(Appointment.class, apptId);

        System.out.println("✓ Appointment created WITHOUT Prescription");
        System.out.println("  Appointment: " + loadedAppt);
        System.out.println("  Has Prescription: " + loadedAppt.hasPrescription());
        System.out.println("  Prescription value: " + loadedAppt.getPrescription());

        assertFalse(loadedAppt.hasPrescription());
        assertNull(loadedAppt.getPrescription());
    }

    /**
     * Step 4: Read Appointment and safely check for Prescription.
     * Demonstrate null-safe access pattern.
     */
    @Test
    public void testNullSafeAccessToPrescription() {
        System.out.println("Step 4: Null-safe access to Prescription");
        transaction.begin();

        Department surgery = new Department("Surgery", "Floor 3", "Dr. Anderson");
        Doctor surgeon = new Doctor("Dr. Anderson", "General Surgery", "GS" + System.nanoTime(), surgery);

        // Appointment with prescription
        Appointment appt1 = new Appointment(
            LocalDateTime.of(2026, 3, 20, 9, 0),
            "Post-operative wound check"
        );
        appt1.setStatus("COMPLETED");
        Prescription presc1 = new Prescription(
            "Cefixime 400mg + Diclofenac 50mg",
            "Cefixime: 1 tablet x 2 times daily for 5 days\nDiclofenac: 1 tablet x 2 times daily for pain",
            LocalDate.of(2026, 3, 20)
        );
        appt1.setPrescription(presc1);

        // Appointment without prescription
        Appointment appt2 = new Appointment(
            LocalDateTime.of(2026, 3, 21, 10, 30),
            "Pre-operative consultation"
        );
        appt2.setStatus("SCHEDULED");
        // No prescription

        surgeon.getAppointments().add(appt1);
        surgeon.getAppointments().add(appt2);

        entityManager.persist(surgery);
        entityManager.persist(surgeon);
        entityManager.persist(appt1);
        entityManager.persist(appt2);
        entityManager.persist(presc1);
        entityManager.flush();
        Long docId = surgeon.getId();
        transaction.commit();

        entityManager.clear();
        Doctor loadedSurgeon = entityManager.find(Doctor.class, docId);

        System.out.println("✓ Loaded Appointments with and without Prescriptions");
        System.out.println("  Doctor: " + loadedSurgeon.getName());
        System.out.println("\n  Appointment 1 (WITH prescription):");
        Appointment loadedAppt1 = loadedSurgeon.getAppointments().get(0);
        if (loadedAppt1.hasPrescription()) {
            System.out.println("    Reason: " + loadedAppt1.getReason());
            System.out.println("    Prescription: " + loadedAppt1.getPrescription().getMedicines());
            System.out.println("    Active: " + loadedAppt1.getPrescription().isActive());
        }

        System.out.println("\n  Appointment 2 (WITHOUT prescription):");
        Appointment loadedAppt2 = loadedSurgeon.getAppointments().get(1);
        System.out.println("    Reason: " + loadedAppt2.getReason());
        System.out.println("    Has Prescription: " + loadedAppt2.hasPrescription());
        if (!loadedAppt2.hasPrescription()) {
            System.out.println("    → No prescription issued yet");
        }

        assertTrue(loadedAppt1.hasPrescription());
        assertFalse(loadedAppt2.hasPrescription());
    }

    /**
     * Step 5: Test prescription lifecycle - active/inactive states.
     */
    @Test
    public void testPrescriptionActiveStatus() {
        System.out.println("Step 5: Test prescription active status (optional vs mandatory)");
        transaction.begin();

        Department cardiology = new Department("Cardiology", "Floor 4", "Dr. Turner");
        Doctor cardiologist = new Doctor("Dr. Turner", "Cardiology", "CARD001", cardiology);

        Appointment appointment = new Appointment(
            LocalDateTime.of(2026, 3, 25, 11, 0),
            "Hypertension management"
        );

        // Active prescription
        Prescription activePrescript = new Prescription(
            "Lisinopril 10mg",
            "1 tablet once daily in the morning",
            LocalDate.of(2026, 3, 25),
            true  // isActive=true
        );

        appointment.setPrescription(activePrescript);
        cardiologist.getAppointments().add(appointment);

        entityManager.persist(cardiology);
        entityManager.persist(cardiologist);
        Long apptId = appointment.getId();
        transaction.commit();

        // Load and verify
        transaction.begin();
        Appointment loadedAppt = entityManager.find(Appointment.class, apptId);
        assertTrue(loadedAppt.getPrescription().isActive());
        System.out.println("✓ Prescription status checked");
        System.out.println("  Prescription: " + loadedAppt.getPrescription().getMedicines());
        System.out.println("  Active: " + loadedAppt.getPrescription().isActive());

        // Change status
        loadedAppt.getPrescription().setActive(false);
        transaction.commit();

        entityManager.clear();
        Appointment verifyAppt = entityManager.find(Appointment.class, apptId);
        assertFalse(verifyAppt.getPrescription().isActive());
        System.out.println("  Updated Active: " + verifyAppt.getPrescription().isActive());
    }

    /**
     * Step 6: Test difference between optional=true and optional=false.
     */
    @Test
    public void testOptionalVsMandatoryPrescription() {
        System.out.println("\nStep 6: REPORT - optional=true vs optional=false");
        System.out.println("=" .repeat(70));

        String report = """
        
        ════════════════════════════════════════════════════════════════════════
        TASK 4: Unidirectional One-to-One with optional Relationship
        ════════════════════════════════════════════════════════════════════════
        
        DATABASE SCHEMA:
        ┌──────────────────────┐          ┌──────────────────────┐
        │   appointments       │          │   prescriptions      │
        ├──────────────────────┤          ├──────────────────────┤
        │ id (PK)              │◀─────────│ id (PK)               │
        │ appoint_date         │ 1 : 0-1  │ medicines            │
        │ status               │          │ dosage               │
        │ reason               │          │ issued_date          │
        │ prescription_id (FK)  │          │ is_active            │
        │ (nullable=true)      │          │ (no appt ref)        │
        └──────────────────────┘          └──────────────────────┘
        
        KEY DIFFERENCES: optional=true vs optional=false:
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        1. ANNOTATION:
        
           optional=true (current in Task 4):
           @OneToOne(optional = true)
           @JoinColumn(name = "prescription_id")
           private Prescription prescription;
           
           optional=false:
           @OneToOne(optional = false)
           @JoinColumn(name = "prescription_id", nullable = false)
           private Prescription prescription;
        
        2. DATABASE CONSTRAINT:
        
           optional=true:
           • prescription_id can be NULL
           • FK column: prescription_id INT NULL
           • Appointment CAN exist without a Prescription
           • Example: Routine checkup with no medication issued
        
           optional=false:
           • prescription_id cannot be NULL
           • FK column: prescription_id INT NOT NULL
           • Appointment MUST have a Prescription
           • Example: All treated infections must have prescription
        
        3. ENTITY CONSTRAINTS:
        
           optional=true:
           • prescription can be null
           • Must check with if (appt.getPrescription() != null)
           • Flexibility: Not every appointment needs a prescription
           
           optional=false:
           • prescription is never null
           • Direct access: appt.getPrescription().getMedicines()
           • Strictness: Every appointment MUST issue a prescription
        
        4. REAL-WORLD SCENARIOS:
        
           USE optional=true WHEN:
           ✓ Not all appointments need prescriptions
           ✓ Example scenarios:
             • Routine checkup - may not need medication
             • Consultation appointment - doctor asks questions only
             • Post-operative review - may not prescribe anything
             • Vaccination appointment - no medication, just vaccine
           
           USE optional=false WHEN:
           ✓ Every appointment MUST result in a prescription
           ✓ Example scenarios:
             • Disease treatment appointment - always prescribe medicine
             • Antibiotic therapy session - must issue prescription
             • Chronic disease management - always prescribe
             • Pain management - always need pain medication prescription
        
        5. HANDLING OPTIONAL PRESCRIPTIONS IN CODE:
        
           Pattern for optional=true:
           ────────────────────────────────────────
           Appointment appt = em.find(Appointment.class, 1);
           
           if (appt.getPrescription() != null) {
               System.out.println(appt.getPrescription().getMedicines());
           } else {
               System.out.println("No prescription issued");
           }
           
           // Or using helper method:
           if (appt.hasPrescription()) {
               // Process prescription
           }
        
        6. CLINIC MANAGEMENT LOGIC:
        
           With optional=true (Hospital System):
           ─────────────────────────────────────
           • Doctor completes appointment
           • Might or might not issue prescription
           • Flexibility for various appointment types
           
           Appointment types:
           ┌─ Routine Checkup (no prescription) ─┐
           │                                      │
           ├─ Consultation (no prescription)     │
           │                                      │
           ├─ Infection Treatment (prescription) │
           │                                      │
           └─ Follow-up (maybe prescription)    │
        
        7. DATABASE INSERT EXAMPLES:
        
           With optional=true (NULL allowed):
           ───────────────────────────────────
           INSERT INTO appointments VALUES (1, '2026-03-10', 'COMPLETED', ..., 5);
           INSERT INTO appointments VALUES (2, '2026-03-11', 'COMPLETED', ..., NULL);
           ↑ Appointment 2 has no prescription
           
           With optional=false (NULL NOT allowed):
           ──────────────────────────────────────
           INSERT INTO appointments VALUES (1, '2026-03-10', 'COMPLETED', ..., 5);
           INSERT INTO appointments VALUES (2, '2026-03-11', 'COMPLETED', ..., NULL);
           ↑ This would fail! NOT NULL constraint violation
        
        8. WHEN THIS RELATIONSHIP EXISTS (1:1):
        
           Why Uni 1:1? (Not Bi 1:1)
           ────────────────────────────
           • Prescription doesn't need to know about Appointment
           • Reverse navigation rarely needed in practice
           • Appointments are created, then prescription issued
           • We don't query "Find appointment for prescription ID 123"
           • Unidirectional is simpler, sufficient for business logic
        
        9. COMPARISON WITH TASK 1:
        
           Task 1: Patient ← → MedicalRecord
           • Medical record is like a patient's profile
           • Always exists for a patient
           • Can have optional=false (patient must have record)
           
           Task 4: Appointment → Prescription (or not)
           • Prescription is issued during/after appointment
           • Might not be needed
           • optional=true is appropriate
        
        ════════════════════════════════════════════════════════════════════════
        
        HOSPITAL DOMAIN PERSPECTIVE:
        
        When should prescription be mandatory (optional=false)?
        ──────────────────────────────────────────────────────
        • Treatment appointments for diseases
        • Pharmacy refill appointments
        • Controlled substance management appointments
        
        When should prescription be optional (optional=true)?
        ─────────────────────────────────────────────────────
        • Routine checkups
        • Consultations
        • Follow-up appointments (may not need new prescription)
        • Diagnostic appointments (just tests, no medication)
        • Preventive care appointments
        
        Current Implementation: optional=true
        ────────────────────────────────────
        More flexible for diverse appointment types in a hospital.
        Different departments can use appointments differently:
        • Surgery: Mostly optional (post-op reviews)
        • Neurology: Mostly mandatory (epilepsy, Parkinson's)
        • General Medicine: Mix of both
        
        ════════════════════════════════════════════════════════════════════════
        """;

        System.out.println(report);
    }
}




