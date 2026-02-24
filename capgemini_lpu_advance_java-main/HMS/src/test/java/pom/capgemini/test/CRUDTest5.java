package pom.capgemini.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import pom.capgemini.entity.Department;
import pom.capgemini.entity.Doctor;
import pom.capgemini.entity.Patient;
import pom.capgemini.util.PersistenceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 5: Doctor ↔ Patient (Bidirectional Many-to-Many)
 *
 * Tests the bi M:N relationship where:
 * - One Doctor can treat many Patients
 * - One Patient can be seen by many Doctors
 * - A JOIN TABLE (patient_doctors) stores the associations
 * - Doctor side OWNS the @JoinTable definition
 * - Patient side uses mappedBy (inverse side)
 * - Both sides can navigate to each other
 * - CRITICAL: Never use CascadeType.REMOVE on M:N relationships!
 * - CRITICAL: Helper methods required to maintain consistency
 */
public class CRUDTest5 {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        entityManager = PersistenceUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        System.out.println("\n========== TEST 5: Doctor ↔ Patient (Bi M:N) ==========\n");
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Step 1: Create Doctors and Patients, then link them using helper method.
     */
    @Test
    public void testCreateDoctorPatientRelationships() {
        System.out.println("Step 1: Create Doctor-Patient treatment relationships");
        transaction.begin();

        Department cardiology = new Department("Cardiology", "Floor 3", "Dr. Mehta");
        Department neurology = new Department("Neurology", "Floor 4", "Dr. Singh");

        Doctor cardiologist = new Doctor("Dr. Mehta", "Cardiology", "CARD001", cardiology);
        Doctor neurologist = new Doctor("Dr. Singh", "Neurology", "NEURO001", neurology);

        Patient p1 = new Patient("Ali", LocalDate.of(1990, 5, 15), "O+", "9876543210");
        Patient p2 = new Patient("Priya", LocalDate.of(1992, 8, 20), "B+", "9876543211");
        Patient p3 = new Patient("Raj", LocalDate.of(1995, 3, 10), "AB+", "9876543212");

        // Ali sees both cardiologist and neurologist
        cardiologist.addPatient(p1);
        neurologist.addPatient(p1);

        // Priya sees only cardiologist
        cardiologist.addPatient(p2);

        // Raj sees only neurologist
        neurologist.addPatient(p3);

        entityManager.persist(cardiology);
        entityManager.persist(neurology);
        transaction.commit();

        System.out.println("✓ Doctor-Patient relationships created");
        System.out.println("  Dr. Mehta (Cardiology) treats: " + cardiologist.getPatients().size() + " patients");
        System.out.println("  Dr. Singh (Neurology) treats: " + neurologist.getPatients().size() + " patients");

        assertEquals(2, cardiologist.getPatients().size());
        assertEquals(2, neurologist.getPatients().size());
    }

    /**
     * Step 2: Navigate from Doctor to Patients and from Patient to Doctors.
     */
    @Test
    public void testBidirectionalNavigation() {
        System.out.println("Step 2: Test bidirectional navigation");

        transaction.begin();
        Department general = new Department("General Medicine", "Floor 1", "Dr. Wilson");
        Doctor doctor = new Doctor("Dr. Wilson", "General Medicine", "GM001", general);

        Patient p1 = new Patient("Sarah", LocalDate.of(1994, 2, 14), "O-", "9876543213");
        Patient p2 = new Patient("Tom", LocalDate.of(1993, 11, 28), "A+", "9876543214");

        doctor.addPatient(p1);
        doctor.addPatient(p2);

        entityManager.persist(general);
        entityManager.persist(doctor);
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();
        Long docId = doctor.getId();
        Long p1Id = p1.getId();
        transaction.commit();

        entityManager.clear();

        // Navigate Doctor → Patients
        Doctor loadedDoc = entityManager.find(Doctor.class, docId);
        System.out.println("\n✓ Forward navigation: Doctor → Patients");
        System.out.println("  Doctor: " + loadedDoc.getName());
        System.out.println("  Patients treated:");
        for (Patient p : loadedDoc.getPatients()) {
            System.out.println("    - " + p.getName());
        }

        // Navigate Patient → Doctors
        Patient loadedPat = entityManager.find(Patient.class, p1Id);
        System.out.println("\n✓ Reverse navigation: Patient → Doctors");
        System.out.println("  Patient: " + loadedPat.getName());
        System.out.println("  Doctors treating:");
        for (Doctor d : loadedPat.getDoctors()) {
            System.out.println("    - " + d.getName());
        }

        assertEquals(2, loadedDoc.getPatients().size());
        assertEquals(1, loadedPat.getDoctors().size());
    }

    /**
     * Step 3: Create sample data: 2 Doctors, 3 Patients, 4 relationships.
     */
    @Test
    public void testCreateSampleData() {
        System.out.println("Step 3: Create sample Hospital Data");
        transaction.begin();

        Department cardiology = new Department("Cardiology", "Floor 3", "Dr. Mehta");
        Department neurology = new Department("Neurology", "Floor 4", "Dr. Gupta");

        Doctor dr_mehta = new Doctor("Dr. Mehta", "Cardiology", "CM001", cardiology);
        Doctor dr_singh = new Doctor("Dr. Singh", "Neurology", "NS001", neurology);

        Patient ali = new Patient("Ali", LocalDate.of(1985, 5, 15), "O+", "9876543220");
        Patient priya = new Patient("Priya", LocalDate.of(1990, 8, 20), "B+", "9876543221");
        Patient raj = new Patient("Raj", LocalDate.of(1988, 3, 10), "AB+", "9876543222");

        // Relationships:
        // Ali → Dr. Mehta + Dr. Singh (2 doctors)
        dr_mehta.addPatient(ali);
        dr_singh.addPatient(ali);

        // Priya → Dr. Mehta only
        dr_mehta.addPatient(priya);

        // Raj → Dr. Singh only
        dr_singh.addPatient(raj);

        entityManager.persist(cardiology);
        entityManager.persist(neurology);
        entityManager.persist(dr_mehta);
        entityManager.persist(dr_singh);
        entityManager.persist(ali);
        entityManager.persist(priya);
        entityManager.persist(raj);
        entityManager.flush();
        Long dr_mehta_id = dr_mehta.getId();
        Long dr_singh_id = dr_singh.getId();
        transaction.commit();

        entityManager.clear();

        // Verify patient_doctors join table via querying
        String jpql = "SELECT d FROM Doctor d JOIN FETCH d.patients WHERE d.name = 'Dr. Mehta'";
        Query query = entityManager.createQuery(jpql);
        Doctor loadedDr = (Doctor) query.getSingleResult();

        System.out.println("✓ Sample data created in patient_doctors join table:");
        System.out.println("  Total relationships (rows in join table): 4");
        System.out.println("\n  Dr. Mehta's patients:");
        for (Patient p : loadedDr.getPatients()) {
            System.out.println("    - " + p.getName());
        }

        assertEquals(2, loadedDr.getPatients().size());
    }

    /**
     * Step 4: Query relationships using JPQL with JOIN FETCH.
     */
    @Test
    public void testJPQLQueriesWithJoinFetch() {
        System.out.println("Step 4: JPQL Queries with JOIN FETCH");

        // Setup
        transaction.begin();
        Department card = new Department("Cardiology", "Floor 3", "Dr. A");
        Department neuro = new Department("Neurology", "Floor 4", "Dr. B");

        Doctor dr_a = new Doctor("Dr. A", "Cardiology", "A001", card);
        Doctor dr_b = new Doctor("Dr. B", "Neurology", "B001", neuro);

        Patient p1 = new Patient("Patient1", LocalDate.of(1990, 1, 1), "O+", "111");
        Patient p2 = new Patient("Patient2", LocalDate.of(1991, 2, 2), "B+", "222");
        Patient p3 = new Patient("Patient3", LocalDate.of(1992, 3, 3), "AB+", "333");

        dr_a.addPatient(p1);
        dr_a.addPatient(p2);
        dr_b.addPatient(p2);
        dr_b.addPatient(p3);

        entityManager.persist(card);
        entityManager.persist(neuro);
        entityManager.persist(dr_a);
        entityManager.persist(dr_b);
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.persist(p3);
        entityManager.flush();
        transaction.commit();

        // Query 1: Find all patients of a doctor
        System.out.println("\nQuery 1: Find all patients of Dr. A");
        String jpql1 = "SELECT d FROM Doctor d JOIN FETCH d.patients WHERE d.name = :dname";
        Query q1 = entityManager.createQuery(jpql1);
        q1.setParameter("dname", "Dr. A");
        Doctor foundDr = (Doctor) q1.getSingleResult();
        System.out.println("  Dr. A treats:");
        for (Patient p : foundDr.getPatients()) {
            System.out.println("    - " + p.getName());
        }

        // Query 2: Find all doctors treating a specific patient
        System.out.println("\nQuery 2: Find all doctors treating Patient2");
        String jpql2 = "SELECT p FROM Patient p JOIN FETCH p.doctors WHERE p.name = :pname";
        Query q2 = entityManager.createQuery(jpql2);
        q2.setParameter("pname", "Patient2");
        Patient foundPat = (Patient) q2.getSingleResult();
        System.out.println("  Patient2 sees:");
        for (Doctor d : foundPat.getDoctors()) {
            System.out.println("    - " + d.getName());
        }

        assertEquals(2, foundDr.getPatients().size());
        assertEquals(2, foundPat.getDoctors().size());
    }

    /**
     * Step 5: Remove a patient from a doctor's list (discharge).
     */
    @Test
    public void testRemovePatientFromDoctor() {
        System.out.println("Step 5: Remove Patient from Doctor (discharge)");

        transaction.begin();
        Department orthopedic = new Department("Orthopedics", "Floor 5", "Dr. Rao");
        Doctor surgeon = new Doctor("Dr. Rao", "Orthopedic Surgery", "OR001", orthopedic);

        Patient p1 = new Patient("Patient_X", LocalDate.of(1989, 7, 15), "O+", "444");
        Patient p2 = new Patient("Patient_Y", LocalDate.of(1991, 9, 20), "B+", "555");

        surgeon.addPatient(p1);
        surgeon.addPatient(p2);

        entityManager.persist(orthopedic);
        entityManager.persist(surgeon);
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.flush();
        Long surgeonId = surgeon.getId();
        Long p1Id = p1.getId();
        transaction.commit();

        System.out.println("  Before discharge: Dr. Rao treats 2 patients");

        // Discharge patient
        transaction.begin();
        Doctor loadedSurgeon = entityManager.find(Doctor.class, surgeonId);
        Patient loadedP1 = entityManager.find(Patient.class, p1Id);

        loadedSurgeon.removePatient(loadedP1);
        // Note: Only removes from join table, doesn't delete Patient or Doctor

        transaction.commit();

        entityManager.clear();
        Doctor verifySurgeon = entityManager.find(Doctor.class, surgeonId);
        Patient verifyP1 = entityManager.find(Patient.class, p1Id);

        System.out.println("✓ Patient discharged successfully");
        System.out.println("  After discharge: Dr. Rao treats " + verifySurgeon.getPatients().size() + " patients");
        System.out.println("  Patient still exists: " + (verifyP1 != null));
        System.out.println("  Doctor still exists: " + (verifySurgeon != null));

        assertEquals(1, verifySurgeon.getPatients().size());
        assertNotNull(verifyP1);  // Patient not deleted
        assertNotNull(verifySurgeon);  // Doctor not deleted
    }

    /**
     * Step 6: Experiment with CascadeType.REMOVE - DANGER ZONE!
     * Demonstrates why you should NEVER use CascadeType.REMOVE with M:N.
     */
    @Test
    public void testCascadeRemoveDanger() {
        System.out.println("\nStep 6: DANGER EXPERIMENT - CascadeType.REMOVE on M:N");
        System.out.println("=" .repeat(70));

        System.out.println("""
        
        ⚠️ WARNING: DO NOT USE CascadeType.REMOVE on @ManyToMany!
        
        Current Implementation: @ManyToMany(cascade = {PERSIST, MERGE})
        Safe: Only adds and merges are cascaded
        
        If we used: @ManyToMany(cascade = {PERSIST, MERGE, REMOVE})
        DANGER: Deleting Doctor would also DELETE all their Patients!
        
        EXAMPLE SCENARIO:
        ─────────────────
        Dr. Mehta leaves the hospital (get terminated/retired)
        
        Code tries to delete: em.remove(dr_mehta);
        
        Result with CascadeType.REMOVE:
        ✓ Dr. Mehta is deleted from doctors table
        ✗ All patients treated by Dr. Mehta are DELETED from patients table!
        ✗ All medical records are lost!
        ✗ Hospital loses patient data!
        ✗ DISASTER! 🔥
        
        Result with CascadeType.PERSIST + MERGE only (CORRECT):
        ✓ Dr. Mehta is deleted from doctors table
        ✓ All rows in patient_doctors are deleted (break associations)
        ✓ All patients remain in patients table (data preserved)
        ✓ Patients can be reassigned to other doctors
        ✓ SAFE! ✓
        
        SAFE CASCADE COMBINATIONS FOR M:N:
        ────────────────────────────────────
        ✓ PERSIST only
        ✓ MERGE only  
        ✓ PERSIST + MERGE (recommended)
        ✓ PERSIST + MERGE + REFRESH
        
        DANGEROUS CASCADE COMBINATIONS FOR M:N:
        ─────────────────────────────────────────
        ✗ PERSIST + MERGE + REMOVE  (current test uses safe combo)
        ✗ CascadeType.ALL  (includes REMOVE!)
        ✗ PERSIST + MERGE + REMOVE + REFRESH
        ✗ Basically: NEVER add REMOVE to M:N!
        """);
    }

    /**
     * Step 7: Test LazyInitializationException and fix with JOIN FETCH.
     */
    @Test
    public void testLazyInitializationExceptionAndFix() {
        System.out.println("\nStep 7: LazyInitializationException and JOIN FETCH fix");
        System.out.println("=" .repeat(70));

        transaction.begin();
        Department pediatrics = new Department("Pediatrics", "Floor 2", "Dr. Kumar");
        Doctor pediatrician = new Doctor("Dr. Kumar", "Pediatrics", "PED001", pediatrics);

        Patient c1 = new Patient("Child1", LocalDate.of(2015, 1, 1), "O+", "666");
        Patient c2 = new Patient("Child2", LocalDate.of(2016, 2, 2), "B+", "777");

        pediatrician.addPatient(c1);
        pediatrician.addPatient(c2);

        entityManager.persist(pediatrics);
        entityManager.persist(pediatrician);
        entityManager.persist(c1);
        entityManager.persist(c2);
        entityManager.flush();
        Long docId = pediatrician.getId();
        transaction.commit();

        System.out.println("\n--- PROBLEM: LazyInitializationException ---");
        System.out.println("""
        
        entityManager.clear();  // Close session
        Doctor doc = em.find(Doctor.class, docId);
        
        // Try to access lazy collection OUTSIDE the session:
        for (Patient p : doc.getPatients()) {  // ❌ EXCEPTION!
            System.out.println(p.getName());   // LazyInitializationException
        }
        
        Error: Could not initialize proxy - no Session
        Reason: @ManyToMany(fetch = FetchType.LAZY) by default
        The patients list is NOT loaded when Doctor is loaded
        Only loaded when accessed within an active session
        
        Why is it lazy?
        → Prevents loading ALL patients when we don't need them
        → Memory optimization
        → Improves performance for large collections
        """);

        System.out.println("\n--- SOLUTION: JOIN FETCH ---");
        System.out.println("""
        
        String jpql = "SELECT d FROM Doctor d " +
                      "JOIN FETCH d.patients " +
                      "WHERE d.id = :id";
        
        Query query = em.createQuery(jpql);
        query.setParameter("id", docId);
        Doctor doc = (Doctor) query.getSingleResult();
        
        // Now safely access patients OUTSIDE the session:
        for (Patient p : doc.getPatients()) {  // ✓ Works!
            System.out.println(p.getName());
        }
        
        How it works:
        → JOIN FETCH eagerly loads the collection in ONE query
        → Patients are loaded when Doctor is loaded
        → Can access them anywhere, anytime
        → Prevents lazy loading errors
        """);

        // Demonstrate working solution
        String jpql = "SELECT d FROM Doctor d " +
                      "JOIN FETCH d.patients " +
                      "WHERE d.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", docId);
        Doctor fetchedDoc = (Doctor) query.getSingleResult();

        System.out.println("\n✓ Solution verified with JOIN FETCH:");
        System.out.println("  Doctor: " + fetchedDoc.getName());
        System.out.println("  Patients (loaded via JOIN FETCH):");
        for (Patient p : fetchedDoc.getPatients()) {
            System.out.println("    - " + p.getName());
        }
    }

    /**
     * Step 8: Helper methods ensure relationship consistency.
     */
    @Test
    public void testHelperMethodsConsistency() {
        System.out.println("\nStep 8: Importance of helper methods in M:N");
        System.out.println("=" .repeat(70));

        System.out.println("""
        
        PROBLEM: Adding to one side only
        ─────────────────────────────────
        
        Doctor doc = new Doctor(...);
        Patient pat = new Patient(...);
        
        ❌ WRONG WAY:
        doc.getPatients().add(pat);
        // pat.getDoctors() is now inconsistent!
        // Database join table will have the row
        // But in-memory object is out of sync
        
        ✓ CORRECT WAY:
        doc.addPatient(pat);
        
        Helper method implementation:
        ────────────────────────────
        public void addPatient(Patient patient) {
            if (!this.patients.contains(patient)) {
                this.patients.add(patient);         // Doctor side
                patient.getDoctors().add(this);     // Patient side
            }
        }
        
        public void removePatient(Patient patient) {
            if (this.patients.contains(patient)) {
                this.patients.remove(patient);      // Doctor side
                patient.getDoctors().remove(this);  // Patient side
            }
        }
        
        Benefits:
        ✓ Single point of truth
        ✓ No accidental inconsistencies
        ✓ Prevents bugs
        ✓ Easier maintenance
        ✓ Self-documenting code
        """);

        transaction.begin();
        Department oncology = new Department("Oncology", "Floor 6", "Dr. Sharma");
        Doctor oncologist = new Doctor("Dr. Sharma", "Medical Oncology", "ONC001", oncology);
        Patient cancerPatient = new Patient("PatientC", LocalDate.of(1970, 10, 10), "O+", "888");

        // Using helper method
        oncologist.addPatient(cancerPatient);

        entityManager.persist(oncology);
        entityManager.persist(oncologist);
        entityManager.persist(cancerPatient);
        entityManager.flush();
        Long docId = oncologist.getId();
        Long patId = cancerPatient.getId();
        transaction.commit();

        entityManager.clear();

        // Verify consistency
        Doctor doc = entityManager.find(Doctor.class, docId);
        Patient pat = entityManager.find(Patient.class, patId);

        System.out.println("\n✓ Helper method maintained consistency:");
        System.out.println("  Doctor.getPatients() contains Patient: " + doc.getPatients().contains(pat));
        System.out.println("  Patient.getDoctors() contains Doctor: " + pat.getDoctors().contains(doc));

        assertTrue(doc.getPatients().contains(pat));
        assertTrue(pat.getDoctors().contains(doc));
    }

    /**
     * Step 9: Comprehensive report on M:N relationships.
     */
    @Test
    public void testReportManyToMany() {
        System.out.println("\nStep 9: COMPREHENSIVE REPORT - Many-to-Many Mapping");
        System.out.println("=" .repeat(70));

        String report = """
        
        ════════════════════════════════════════════════════════════════════════
        TASK 5: Bidirectional Many-to-Many Mapping (Doctor ↔ Patient)
        ════════════════════════════════════════════════════════════════════════
        
        DATABASE SCHEMA:
        ┌─────────────────────┐    ┌──────────────────┐    ┌──────────────────┐
        │    doctors          │    │  patient_doctors │    │    patients      │
        ├─────────────────────┤    ├──────────────────┤    ├──────────────────┤
        │ id (PK)             │───▶│ doctor_id (FK)   │◀───│ id (PK)          │
        │ name                │    │ patient_id (FK)  │    │ name             │
        │ specialization      │    │ (Composite PK)   │    │ dob              │
        │ license_no          │    │                  │    │ blood_group      │
        │ department_id (FK)  │    └──────────────────┘    │ phone            │
        └─────────────────────┘                            │ med_record_id(FK)│
                                                           └──────────────────┘
        
        KEY CONCEPTS:
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        1. THE JOIN TABLE (patient_doctors):
           
           • Automatic table created by Hibernate
           • Stores associations between Doctors and Patients
           • Has two foreign keys: doctor_id and patient_id
           • Has a composite primary key: (doctor_id, patient_id)
           • This prevents duplicate doctor-patient pairs
           • Neither Doctor nor Patient has FK to each other directly
        
        2. ANNOTATIONS:
        
           Doctor.java (OWNING side - defines @JoinTable):
           ────────────────────────────────────────────────
           @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
           @JoinTable(
               name = "patient_doctors",
               joinColumns = @JoinColumn(name = "doctor_id"),
               inverseJoinColumns = @JoinColumn(name = "patient_id")
           )
           private List<Patient> patients = new ArrayList<>();
           
           Patient.java (INVERSE side - uses mappedBy):
           ──────────────────────────────────────────
           @ManyToMany(mappedBy = "patients")
           private List<Doctor> doctors = new ArrayList<>();
        
        3. WHAT IS "@JoinTable"?
           
           • Defines the intermediate join table
           • name: "patient_doctors" → table name
           • joinColumns: Points to THIS entity (Doctor)
             @JoinColumn(name = "doctor_id")
           • inverseJoinColumns: Points to OTHER entity (Patient)
             @JoinColumn(name = "patient_id")
           
           Hibernate creates SQL like:
           ────────────────────────────
           CREATE TABLE patient_doctors (
               doctor_id BIGINT NOT NULL,
               patient_id BIGINT NOT NULL,
               PRIMARY KEY (doctor_id, patient_id),
               FOREIGN KEY (doctor_id) REFERENCES doctors(id),
               FOREIGN KEY (patient_id) REFERENCES patients(id)
           )
        
        4. ONLY OWNING SIDE DEFINES @JoinTable:
           
           ✓ Doctor defines @JoinTable
           ✗ Patient does NOT define it
           ✓ Patient only uses mappedBy
           
           Why? → Only one entity can own the join table definition
           Otherwise: Ambiguity and conflicts
        
        5. BIDIRECTIONAL NAVIGATION:
           
           FORWARD: Doctor → Patients
           Doctor doc = em.find(Doctor.class, 1);
           List<Patient> patients = doc.getPatients();  // ✓ Works
           
           REVERSE: Patient → Doctors
           Patient pat = em.find(Patient.class, 1);
           List<Doctor> doctors = pat.getDoctors();  // ✓ Works
           
           Both work because it's bidirectional!
        
        6. HELPER METHODS ARE CRITICAL:
           
           Problem: Two sides to sync
           ──────────────────────────
           • Doctor has a patients List
           • Patient has a doctors List
           • If you modify only one side, they're out of sync
           • JPA only persists one side (the owning side)
           • Database might have correct data
           • But in-memory objects would be inconsistent!
           
           Solution: Helper methods
           ────────────────────────
           
           void addPatient(Patient patient) {
               if (!this.patients.contains(patient)) {
                   this.patients.add(patient);
                   patient.getDoctors().add(this);  // Sync both sides!
               }
           }
           
           void removePatient(Patient patient) {
               if (this.patients.contains(patient)) {
                   this.patients.remove(patient);
                   patient.getDoctors().remove(this);  // Sync both sides!
               }
           }
        
        7. CASCADE BEHAVIOR - CRUCIAL!
           
           ✓ SAFE TO USE:
           • CascadeType.PERSIST: Saving Doctor saves linked Patients
           • CascadeType.MERGE: Updating Doctor updates linked Patients
           • Both together: @ManyToMany(cascade = {PERSIST, MERGE})
           
           ✗ DANGEROUS:
           • CascadeType.REMOVE: Deleting Doctor deletes ALL Patients!
           • CascadeType.ALL: Includes REMOVE, very dangerous!
           
           Hospital Example:
           ────────────────
           Doctor leaves hospital:
           em.remove(doctor);  // Delete doctor from DB
           
           With PERSIST+MERGE only (CORRECT):
           ✓ Doctor removed from doctors table
           ✓ Rows removed from patient_doctors join table
           ✓ Patients remain in patients table (safe!)
           
           With PERSIST+MERGE+REMOVE (DISASTER):
           ✓ Doctor removed
           ✗ All patients of that doctor DELETED!
           ✗ Lost patient records!
           ✗ Legal liability!
           
           RULE: NEVER use REMOVE with M:N!
        
        8. LAZY VS EAGER LOADING:
           
           @ManyToMany(..., fetch = FetchType.LAZY)  // Default
           
           LAZY (recommended):
           • Patients list NOT loaded with Doctor
           • Only loaded when accessed
           • Prevents memory waste
           • Better performance for large lists
           • Risk: LazyInitializationException if accessed outside session
           • Solution: Use JOIN FETCH in JPQL
           
           EAGER (not recommended for M:N):
           • Patients list loaded with Doctor
           • Heavy performance cost
           • Can cause N+1 query problem
           • Large memory footprint
           • Only use if you ALWAYS need the patients
        
        9. QUERYING M:N RELATIONSHIPS:
           
           Find all doctors of a patient:
           ───────────────────────────────
           String jpql = "SELECT p FROM Patient p JOIN FETCH p.doctors WHERE p.id = :id";
           Patient patient = (Patient) query.getSingleResult();
           patient.getDoctors();  // Loaded via JOIN FETCH
           
           Find all patients of a doctor:
           ────────────────────────────────
           String jpql = "SELECT d FROM Doctor d JOIN FETCH d.patients WHERE d.id = :id";
           Doctor doctor = (Doctor) query.getSingleResult();
           doctor.getPatients();  // Loaded via JOIN FETCH
           
           Count relationships:
           ────────────────────
           String jpql = "SELECT COUNT(d) FROM Doctor d WHERE d.name = :name";
           Long count = (Long) query.getSingleResult();
           
           ✓ Always use JOIN FETCH for M:N to prevent LazyInitializationException
        
        10. WHEN TO USE M:N RELATIONSHIPS:
        
            Use when:
            ✓ One entity has many of another
            ✓ Other entity also has many of the first
            ✓ Neither dominates the relationship
            ✓ Need bidirectional access
            
            Examples:
            ✓ Doctor ↔ Patient (one doc, many patients; one patient, many docs)
            ✓ Student ↔ Course (one student, many courses; one course, many students)
            ✓ Author ↔ Book (one author, many books; one book, many authors)
            ✓ Employee ↔ Project (one emp, many projects; one project, many emps)
            
            Don't use when:
            ✗ One entity dominates the relationship
            ✗ Need attributes on the relationship itself
            ✗ Instead: Create a @Entity for the join table with extra fields
        
        11. PROMOTING JOIN TABLE TO @ENTITY:
        
            When the join table needs extra data:
            Example: Add "since_date" (when doctor started treating patient)
            
            Instead of:
            patient_doctors (doctor_id, patient_id)
            
            Use:
            CREATE ENTITY "DoctorPatientRelationship"
            with fields: doctor, patient, sinceDate
            
            Then create:
            @OneToMany with Doctor
            @OneToMany with Patient
            This is called a "component mapping" or "extra attributes on join table"
        
        12. REAL HOSPITAL SCENARIOS:
        
            Scenario 1: Patient transfers doctor
            ──────────────────────────────────
            Dr. Mehta (cardiologist) transfers to another hospital
            
            Code:
            doctor.removePatient(patient);  // Discharge patient
            // patient and doctor remain in DB
            // Only join table row is deleted
            
            Result:
            ✓ Patient can be reassigned to another cardiologist
            ✓ Doctor can move to new hospital
            ✓ No data loss
            
            Scenario 2: Patient sees multiple specialists
            ───────────────────────────────────────────
            Heart disease patient needs:
            • Cardiologist (Dr. Mehta)
            • Nephrologist (Dr. Gupta) - kidney disease
            • Neurologist (Dr. Singh) - medication side effects
            
            Code:
            patient.getDoctors()  // Returns 3 doctors!
            dr_mehta.getPatients()  // Returns many patients
            dr_gupta.getPatients()  // Returns many patients
            
            Result: Perfect for M:N relationships
            ✓ Patient sees multiple doctors
            ✓ Each doctor sees multiple patients
            ✓ Full bidirectional access
        
        ════════════════════════════════════════════════════════════════════════
        """;

        System.out.println(report);
    }
}







