package pom.capgemini.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.MedicalRecord;
import pom.capgemini.entity.Patient;
import pom.capgemini.util.PersistenceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CRUDTest1 {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        entityManager = PersistenceUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        System.out.println("\n========== TEST 1: Patient → MedicalRecord (Uni 1:1) ==========\n");
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @Test
    public void testCreatePatientWithMedicalRecord() {
        System.out.println("Step 1: Persist Patient with MedicalRecord");
        transaction.begin();

        MedicalRecord record = new MedicalRecord(LocalDate.now(), "Flu", "Rest 3 days");
        Patient patient = new Patient("Ali", LocalDate.of(1995, 5, 15), "O+", "9876543210");
        patient.setMedicalRecord(record);

        entityManager.persist(patient);
        transaction.commit();

        System.out.println("✓ Patient persisted with MedicalRecord");
        System.out.println("  Patient: " + patient);
        System.out.println("  MedicalRecord: " + record);
        assertNotNull(patient.getId());
        assertNotNull(record.getId());
    }

    @Test
    public void testReadPatientAndNavigateToMedicalRecord() {
        System.out.println("Step 2: Read Patient and navigate to MedicalRecord");

        // First, persist
        transaction.begin();
        MedicalRecord record = new MedicalRecord(LocalDate.now(), "COVID-19", "Quarantine 2 weeks");
        Patient patient = new Patient("Priya", LocalDate.of(1998, 8, 20), "B+", "9876543211");
        patient.setMedicalRecord(record);
        entityManager.persist(patient);
        Long patientId = patient.getId();
        transaction.commit();

        // Now read
        entityManager.clear();
        Patient loadedPatient = entityManager.find(Patient.class, patientId);
        assertNotNull(loadedPatient);
        assertNotNull(loadedPatient.getMedicalRecord());
        System.out.println("✓ Forward navigation works: Patient → MedicalRecord");
        System.out.println("  Patient: " + loadedPatient);
        System.out.println("  Diagnosis: " + loadedPatient.getMedicalRecord().getDiagnosis());
        assertEquals("COVID-19", loadedPatient.getMedicalRecord().getDiagnosis());
    }

    @Test
    public void testReverseNavigationNotPossible() {
        System.out.println("Step 3: Verify reverse navigation is NOT possible (unidirectional)");

        transaction.begin();
        MedicalRecord record = new MedicalRecord(LocalDate.now(), "Diabetes", "Monitor blood sugar");
        Patient patient = new Patient("Raj", LocalDate.of(2000, 3, 10), "AB+", "9876543212");
        patient.setMedicalRecord(record);
        entityManager.persist(patient);
        Long recordId = record.getId();
        transaction.commit();

        entityManager.clear();
        MedicalRecord loadedRecord = entityManager.find(MedicalRecord.class, recordId);
        assertNotNull(loadedRecord);

        // Try to get patient - this field does NOT exist in MedicalRecord
        // MedicalRecord mr = entityManager.find(MedicalRecord.class, recordId);
        // System.out.println(mr.getPatient()); // This would fail - no such method!

        System.out.println("✓ Confirmed: MedicalRecord has no reference to Patient");
        System.out.println("  MedicalRecord: " + loadedRecord);
        System.out.println("  (No getPatient() method exists in MedicalRecord)");
    }

    @Test
    public void testUpdateMedicalRecordThroughPatient() {
        System.out.println("Step 4: Update MedicalRecord via dirty-checking");

        transaction.begin();
        MedicalRecord record = new MedicalRecord(LocalDate.now(), "Hypertension", "Regular checkups");
        Patient patient = new Patient("Sam", LocalDate.of(1997, 12, 5), "O-", "9876543213");
        patient.setMedicalRecord(record);
        entityManager.persist(patient);
        Long patientId = patient.getId();
        transaction.commit();

        // Load and modify
        transaction.begin();
        Patient loadedPatient = entityManager.find(Patient.class, patientId);
        loadedPatient.getMedicalRecord().setNotes("Updated: Follow-up required in 2 weeks");
        // No explicit merge() needed - dirty-checking handles it
        transaction.commit();

        entityManager.clear();
        Patient verifyPatient = entityManager.find(Patient.class, patientId);
        System.out.println("✓ MedicalRecord updated successfully");
        System.out.println("  New notes: " + verifyPatient.getMedicalRecord().getNotes());
        assertEquals("Updated: Follow-up required in 2 weeks",
                     verifyPatient.getMedicalRecord().getNotes());
    }

    @Test
    public void testCascadeDelete() {
        System.out.println("Step 5: Test CascadeType.ALL (cascade delete)");

        transaction.begin();
        MedicalRecord record = new MedicalRecord(LocalDate.now(), "Arthritis", "Physical therapy");
        Patient patient = new Patient("Maya", LocalDate.of(1994, 7, 25), "AB-", "9876543214");
        patient.setMedicalRecord(record);
        entityManager.persist(patient);
        Long patientId = patient.getId();
        Long recordId = record.getId();
        transaction.commit();

        System.out.println("  Patient and MedicalRecord created:");
        System.out.println("  Patient ID: " + patientId + ", MedicalRecord ID: " + recordId);

        // Delete Patient
        transaction.begin();
        Patient loadedPatient = entityManager.find(Patient.class, patientId);
        entityManager.remove(loadedPatient);
        transaction.commit();

        System.out.println("  ✓ Patient deleted");

        // Verify both are deleted
        entityManager.clear();
        Patient verifyPatient = entityManager.find(Patient.class, patientId);
        MedicalRecord verifyRecord = entityManager.find(MedicalRecord.class, recordId);

        assertNull(verifyPatient);
        assertNull(verifyRecord);
        System.out.println("✓ Cascade delete successful: MedicalRecord was also deleted automatically");
    }

    @Test
    public void testOptionalMedicalRecord() {
        System.out.println("Step 6: Test optional medical record (FK can be NULL)");

        // Current mapping has medicalRecord, but we can test with null assignment
        transaction.begin();
        Patient patient = new Patient("John", LocalDate.of(2001, 1, 1), "O+", "9876543215");
        patient.setMedicalRecord(null);  // No medical record yet
        entityManager.persist(patient);
        Long patientId = patient.getId();
        transaction.commit();

        entityManager.clear();
        Patient loadedPatient = entityManager.find(Patient.class, patientId);
        assertNull(loadedPatient.getMedicalRecord());
        System.out.println("✓ Patient can exist without MedicalRecord (med_record_id = NULL)");
        System.out.println("  Patient: " + loadedPatient);
    }

    @Test
    public void testReportUnidirectional() {
        System.out.println("\nStep 7: REPORT - Unidirectional One-to-One Mapping");
        System.out.println("=" .repeat(70));

        String report = """
        
        ════════════════════════════════════════════════════════════════════════
        TASK 1: Unidirectional One-to-One Mapping (Patient → MedicalRecord)
        ════════════════════════════════════════════════════════════════════════
        
        DATABASE SCHEMA:
        ┌─────────────────────┐          ┌──────────────────────┐
        │     patients        │          │  medical_records     │
        ├─────────────────────┤          ├──────────────────────┤
        │ id (PK)             │──1:1────▶│ id (PK)               │
        │ name                │          │ record_date          │
        │ dob                 │          │ diagnosis            │
        │ blood_group         │          │ notes                │
        │ phone               │          │ (no patient ref)     │
        │ med_record_id (FK)  │◀─────────┘                      │
        │ (unique)            │          └──────────────────────┘
        └─────────────────────┘
        
        KEY POINTS ABOUT UNIDIRECTIONAL 1:1:
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        1. FK LOCATION:
           • Foreign key (med_record_id) is in the OWNING side table (patients)
           • MedicalRecord table has NO reference column back to Patient
           • @JoinColumn(name="med_record_id") declares where the FK goes
        
        2. UNIDIRECTIONAL NAVIGATION:
           • FORWARD works ✓: Patient p → p.getMedicalRecord()
           • REVERSE fails ✗: No way to get Patient from MedicalRecord
           • MedicalRecord has NO field like "private Patient patient;"
        
        3. ANNOTATIONS REQUIRED:
           Patient.java:
           @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
           @JoinColumn(name = "med_record_id", unique = true)
           private MedicalRecord medicalRecord;
        
           MedicalRecord.java:
           // NO @OneToOne here - it's the inverse side
           // It doesn't know about Patient
        
        4. CASCADE BEHAVIOR (CascadeType.ALL):
           • When Patient is SAVED → MedicalRecord is saved
           • When Patient is UPDATED → MedicalRecord is updated
           • When Patient is DELETED → MedicalRecord is also DELETED ⚠️
           • When MedicalRecord is deleted → Patient is NOT deleted
        
        5. FETCH STRATEGY (FetchType.LAZY):
           • MedicalRecord is loaded ONLY when explicitly accessed
           • Reduces memory overhead for Patient loads
           • Can cause LazyInitializationException if accessed outside session
        
        6. WHEN TO USE UNIDIRECTIONAL 1:1:
           ✓ When reverse navigation is unnecessary
           ✓ When only one entity needs to "know about" the relationship
           ✓ Simplifies code: no need for helper methods
           ✗ Cannot easily query "Find the Patient for this MedicalRecord"
           ✗ If you need bidirectional nav, add @OneToOne to MedicalRecord with mappedBy
        
        7. COMPARISON: UNI vs BI 1:1
        
           UNIDIRECTIONAL (current):
           • Patient → MedicalRecord ✓
           • MedicalRecord → Patient ✗
           • SimpleR, less maintenance
           
           BIDIRECTIONAL (if we added it):
           • Patient → MedicalRecord ✓
           • MedicalRecord → Patient ✓
           • More complex, need helper methods
           • Must mark one side with @OneToOne(mappedBy="medicalRecord")
        
        8. REAL-WORLD SCENARIOS:
           
           UNIDIRECTIONAL is better when:
           • Patients need medical history (common query direction)
           • Medical records rarely queried from → backwards
           • Example: Display patient profile with their medical record
           
           BIDIRECTIONAL is better when:
           • Need to update medical record and find which patient it belongs to
           • Frequent bidirectional navigation required
           • Example: Search by diagnosis, update record, then update patient status
        
        ════════════════════════════════════════════════════════════════════════
        """;

        System.out.println(report);
    }
}

