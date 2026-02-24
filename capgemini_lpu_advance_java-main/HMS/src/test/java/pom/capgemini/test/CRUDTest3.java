package pom.capgemini.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Appointment;
import pom.capgemini.entity.Department;
import pom.capgemini.entity.Doctor;
import pom.capgemini.util.PersistenceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 3: Doctor → Appointment (Unidirectional One-to-Many)
 *
 * Tests the uni 1:N relationship where:
 * - One Doctor has many Appointments
 * - Appointment has NO reference back to Doctor
 * - Doctor owns the FK (doctor_id) via @JoinColumn on the collection
 * - Navigation is one-way: Doctor → Appointment only
 * - This is different from Task 2 (mappedBy) - here we use @JoinColumn directly on List
 */
public class CRUDTest3 {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        entityManager = PersistenceUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        System.out.println("\n========== TEST 3: Doctor → Appointment (Uni 1:N) ==========\n");
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Step 1: Create Appointment entity without Doctor field.
     * Prove that Appointment has no Doctor reference.
     */
    @Test
    public void testAppointmentWithoutDoctorReference() {
        System.out.println("Step 1: Create Appointment without Doctor field");
        transaction.begin();

        // Note: Appointment has NO doctor field - it's unidirectional
        Appointment appt = new Appointment(
            LocalDateTime.of(2026, 2, 28, 10, 30),
            "Routine checkup"
        );
        appt.setStatus("SCHEDULED");

        entityManager.persist(appt);
        Long apptId = appt.getId();
        transaction.commit();

        System.out.println("✓ Appointment created without Doctor reference");
        System.out.println("  Appointment: " + appt);
        System.out.println("  Note: appt.getDoctor() does NOT exist (unidirectional)");
        assertNotNull(appt.getId());
    }

    /**
     * Step 2: Create Doctor with multiple Appointments using @JoinColumn.
     */
    @Test
    public void testCreateDoctorWithAppointments() {
        System.out.println("Step 2: Create Doctor and add Appointments");
        transaction.begin();

        Department cardiology = new Department("Cardiology", "Floor 3", "Dr. Smith");
        Doctor doctor = new Doctor("Dr. Smith", "Cardiology", "LICENSE123", cardiology);

        // Create appointments - no Doctor reference needed
        Appointment appt1 = new Appointment(
            LocalDateTime.of(2026, 3, 1, 9, 0),
            "Cardiac checkup"
        );
        Appointment appt2 = new Appointment(
            LocalDateTime.of(2026, 3, 1, 10, 30),
            "ECG follow-up"
        );
        Appointment appt3 = new Appointment(
            LocalDateTime.of(2026, 3, 1, 2, 0),
            "Blood pressure monitoring"
        );

        // Add to doctor's appointment list
        doctor.getAppointments().add(appt1);
        doctor.getAppointments().add(appt2);
        doctor.getAppointments().add(appt3);

        entityManager.persist(cardiology);
        entityManager.persist(doctor);
        transaction.commit();

        System.out.println("✓ Doctor created with 3 Appointments");
        System.out.println("  Doctor: " + doctor.getName());
        System.out.println("  Appointments:");
        for (Appointment appt : doctor.getAppointments()) {
            System.out.println("    - " + appt.getAppointDate() + " : " + appt.getReason());
        }

        assertEquals(3, doctor.getAppointments().size());
    }

    /**
     * Step 3: Navigate from Doctor to Appointments.
     * Prove forward navigation works.
     */
    @Test
    public void testNavigateDoctorToAppointments() {
        System.out.println("Step 3: Navigate from Doctor to Appointments");

        transaction.begin();
        Department orthopedics = new Department("Orthopedics", "Floor 2", "Dr. Jones");
        Doctor doctor = new Doctor("Dr. Jones", "Orthopedics", "LICENSE456", orthopedics);

        Appointment appt1 = new Appointment(
            LocalDateTime.of(2026, 3, 2, 11, 0),
            "Knee consultation"
        );
        Appointment appt2 = new Appointment(
            LocalDateTime.of(2026, 3, 2, 3, 30),
            "X-ray review"
        );

        doctor.getAppointments().add(appt1);
        doctor.getAppointments().add(appt2);

        entityManager.persist(orthopedics);
        entityManager.persist(doctor);
        Long docId = doctor.getId();
        transaction.commit();

        entityManager.clear();
        Doctor loadedDoctor = entityManager.find(Doctor.class, docId);

        System.out.println("✓ Forward navigation works: Doctor → Appointments");
        System.out.println("  Doctor: " + loadedDoctor.getName());
        System.out.println("  Total appointments: " + loadedDoctor.getAppointments().size());
        for (Appointment appt : loadedDoctor.getAppointments()) {
            System.out.println("    - " + appt.getReason() + " at " + appt.getAppointDate());
        }

        assertEquals(2, loadedDoctor.getAppointments().size());
    }

    /**
     * Step 4: Demonstrate that reverse navigation is NOT possible.
     * Try to find doctor from appointment - it's not possible in uni 1:N with @JoinColumn.
     */
    @Test
    public void testReverseNavigationNotPossible() {
        System.out.println("Step 4: Verify reverse navigation is NOT possible");

        transaction.begin();
        Department pediatrics = new Department("Pediatrics", "Floor 1", "Dr. Brown");
        Doctor doctor = new Doctor("Dr. Brown", "Pediatrics", "LICENSE789", pediatrics);

        Appointment appt = new Appointment(
            LocalDateTime.of(2026, 3, 3, 2, 0),
            "Vaccination"
        );

        doctor.getAppointments().add(appt);
        entityManager.persist(pediatrics);
        entityManager.persist(doctor);
        Long apptId = appt.getId();
        transaction.commit();

        entityManager.clear();
        Appointment loadedAppt = entityManager.find(Appointment.class, apptId);

        System.out.println("✓ Confirmed: Appointment has no Doctor reference");
        System.out.println("  Appointment loaded: " + loadedAppt);
        System.out.println("  appt.getDoctor() does NOT exist - it's unidirectional");
        System.out.println("  You CANNOT find which doctor this appointment belongs to");
        System.out.println("  (Without a direct doctor field or complex query)");

        assertNotNull(loadedAppt);
        // loadedAppt.getDoctor() would fail - method doesn't exist!
    }

    /**
     * Step 5: Update Appointment status through Doctor's list.
     */
    @Test
    public void testUpdateAppointmentStatus() {
        System.out.println("Step 5: Update Appointment status via Doctor's collection");

        transaction.begin();
        Department ent = new Department("ENT", "Floor 5", "Dr. Miller");
        Doctor doctor = new Doctor("Dr. Miller", "ENT", "LICENSE111", ent);

        Appointment appt1 = new Appointment(
            LocalDateTime.of(2026, 3, 4, 9, 0),
            "Hearing test"
        );
        appt1.setStatus("SCHEDULED");

        Appointment appt2 = new Appointment(
            LocalDateTime.of(2026, 3, 4, 10, 30),
            "Throat examination"
        );
        appt2.setStatus("SCHEDULED");

        doctor.getAppointments().add(appt1);
        doctor.getAppointments().add(appt2);

        entityManager.persist(ent);
        entityManager.persist(doctor);
        entityManager.persist(appt1);
        entityManager.persist(appt2);
        entityManager.flush();
        Long docId = doctor.getId();
        Long appt1Id = appt1.getId();
        transaction.commit();

        // Update appointment 1 status
        transaction.begin();
        Appointment loadedAppt1 = entityManager.find(Appointment.class, appt1Id);
        loadedAppt1.setStatus("COMPLETED");
        transaction.commit();

        entityManager.clear();
        Doctor verifyDoctor = entityManager.find(Doctor.class, docId);
        System.out.println("✓ Appointment status updated");
        System.out.println("  Doctor: " + verifyDoctor.getName());
        System.out.println("  Appointments after update:");
        for (Appointment appt : verifyDoctor.getAppointments()) {
            System.out.println("    - " + appt.getReason() + " [" + appt.getStatus() + "]");
        }

        // Find the specific appointment by its ID to verify status
        Appointment verifyAppt1 = entityManager.find(Appointment.class, appt1Id);
        assertEquals("COMPLETED", verifyAppt1.getStatus());
    }

    /**
     * Step 6: Compare Uni 1:N (with @JoinColumn) vs Bi 1:N (with mappedBy).
     */
    @Test
    public void testComparisonUni1NvsBi1N() {
        System.out.println("Step 6: Uni 1:N vs Bi 1:N Comparison");

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  COMPARISON: Uni 1:N (@JoinColumn) vs Bi 1:N (mappedBy)  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        String comparison = """
        
        ┌──────────────────────────────────────────────────────────────────┐
        │ ASPECT                  │  Uni 1:N (Task 3)  │  Bi 1:N (Task 2) │
        ├──────────────────────────────────────────────────────────────────┤
        │ Forward nav             │ ✓ Doctor → Appts  │ ✓ Dept → Doctors │
        │ Reverse nav             │ ✗ Appt → Doctor   │ ✓ Doc → Dept     │
        │ FK location             │ In MANY table      │ In MANY table    │
        │ Owning entity           │ Doctor             │ Doctor           │
        │ Many side has ref back  │ ✗ NO               │ ✗ NO             │
        │ One side has collection │ ✓ YES              │ ✓ YES            │
        │ Annotation on collection│ @JoinColumn        │ (none on colletn)│
        │ Annotation on ref       │ (no ref)           │ @JoinColumn      │
        │ One side uses mappedBy  │ ✗ NO               │ ✓ YES            │
        │ Helper methods needed   │ ✗ NO               │ ✓ YES            │
        │ Implementation simpler  │ ✓ YES              │ ✗ NO             │
        │ Query flexibility       │ ✗ LIMITED          │ ✓ GOOD           │
        └──────────────────────────────────────────────────────────────────┘
        
        KEY DIFFERENCES:
        
        1. ANNOTATION STYLE:
           
           Uni 1:N (Doctor → Appointment):
           @OneToMany(cascade=CascadeType.ALL)
           @JoinColumn(name="doctor_id")        ← Specifies FK column directly
           private List<Appointment> appointments;
           
           Bi 1:N (Department ↔ Doctor):
           @OneToMany(mappedBy="department")     ← Points to OWNING side field
           @ManyToOne
           @JoinColumn(name="department_id")    ← On the OWNING side
        
        2. DATABASE RESULT:
           Both result in same schema!
           
           appointments table:
           | id | doctor_id (FK) | ...
           
           doctors table:
           | id | department_id (FK) | ...
           
           The difference is purely in HOW we annotate in Java, not in the DB.
        
        3. NAVIGATION POSSIBILITIES:
           
           Task 3 (Uni 1:N):
           Doctor doc = em.find(Doctor.class, 1);
           List<Appointment> appts = doc.getAppointments();  ✓ Works
           
           Appointment appt = em.find(Appointment.class, 1);
           Doctor doc = appt.getDoctor();  ✗ FAILS - field doesn't exist
           
           Task 2 (Bi 1:N):
           Department dept = em.find(Department.class, 1);
           List<Doctor> docs = dept.getDoctors();  ✓ Works
           
           Doctor doc = em.find(Doctor.class, 1);
           Department dept = doc.getDepartment();  ✓ Works (reverse navigation)
        
        4. REAL-WORLD SCENARIOS:
        
           USE UNI 1:N (@JoinColumn) WHEN:
           ✓ Appointments are accessed from Doctor's perspective
           ✓ Never need to find doctor from an appointment
           ✓ Simpler code, no helper methods
           ✓ Example Hospital Scenario:
             "List all appointments for Dr. Smith on March 5th"
             Doctor.getAppointments() gives us what we need
             We don't need to query "Find doctor for appointment ID 123"
           
           USE BI 1:N (mappedBy) WHEN:
           ✓ Need bidirectional navigation
           ✓ Must find parent from child perspective
           ✓ More complex business logic
           ✓ Example Hospital Scenario:
             "Which department does Dr. Smith belong to?"
             Doctor.getDepartment() needed frequently
             Plus "List all doctors in Cardiology"
             Department.getDoctors() also needed frequently
        
        5. WHICH IS BETTER FOR HOSPITAL SYSTEM?
        
           For Doctor-Department: ✓ Use BI 1:N
           Because: Admin frequently asks "Which dept is this doctor in?"
           And: "Which doctors work in Cardiology?"
           
           For Doctor-Appointment: ✓ Use UNI 1:N
           Because: Appointments are mainly accessed from Doctor side
           And: We rarely query "Find doctor for appointment" directly
           And: @JoinColumn is simpler, no helper methods needed
        
        ════════════════════════════════════════════════════════════════════
        """;

        System.out.println(comparison);
    }

    /**
     * Step 6.1: Real hospital scenario for Uni 1:N
     */
    @Test
    public void testHospitalScenarioUni1N() {
        System.out.println("\nStep 6.1: Hospital Scenario - Uni 1:N Use Case");

        transaction.begin();
        Department neurology = new Department("Neurology", "Floor 4", "Dr. Garcia");
        Doctor neurologist = new Doctor("Dr. Garcia", "Neurology", "NEURO001", neurology);

        // Create appointments for March 5th
        Appointment appt1 = new Appointment(
            LocalDateTime.of(2026, 3, 5, 8, 0),
            "Migraine consultation"
        );
        Appointment appt2 = new Appointment(
            LocalDateTime.of(2026, 3, 5, 9, 30),
            "Seizure evaluation"
        );
        Appointment appt3 = new Appointment(
            LocalDateTime.of(2026, 3, 5, 11, 0),
            "Parkinson's review"
        );

        neurologist.getAppointments().add(appt1);
        neurologist.getAppointments().add(appt2);
        neurologist.getAppointments().add(appt3);

        entityManager.persist(neurology);
        entityManager.persist(neurologist);
        Long docId = neurologist.getId();
        transaction.commit();

        entityManager.clear();
        Doctor loadedDoctor = entityManager.find(Doctor.class, docId);

        System.out.println("✓ Hospital Scenario: List all appointments for Dr. Garcia on March 5th");
        System.out.println("  Doctor: " + loadedDoctor.getName());
        System.out.println("  Specialty: " + loadedDoctor.getSpecialization());
        System.out.println("\n  Appointments for March 5th:");
        for (Appointment appt : loadedDoctor.getAppointments()) {
            System.out.println("    " + appt.getAppointDate() + " - " + appt.getReason());
        }

        System.out.println("\n  This is where UNI 1:N (@JoinColumn) is perfect:");
        System.out.println("  • We access appointments via Doctor (forward navigation)");
        System.out.println("  • No need for reverse navigation");
        System.out.println("  • @JoinColumn makes it simple and efficient");
    }
}


