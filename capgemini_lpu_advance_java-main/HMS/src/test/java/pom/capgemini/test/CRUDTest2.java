package pom.capgemini.test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import pom.capgemini.entity.Department;
import pom.capgemini.entity.Doctor;
import pom.capgemini.util.PersistenceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Task 2: Department ↔ Doctor (Bidirectional One-to-Many)
 *
 * Tests the bi 1:N relationship where:
 * - One Department has many Doctors
 * - Each Doctor belongs to one Department
 * - Doctor side OWNS the FK (department_id)
 * - Department side uses mappedBy="department" (inverse side)
 * - Both sides can navigate to each other
 * - Helper methods are CRITICAL to maintain relationship integrity
 */
public class CRUDTest2 {

    private EntityManager entityManager;
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        entityManager = PersistenceUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        System.out.println("\n========== TEST 2: Department ↔ Doctor (Bi 1:N) ==========\n");
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Step 1: Create Departments with Doctors using helper method addDoctor().
     */
    @Test
    public void testCreateDepartmentWithDoctors() {
        System.out.println("Step 1: Create Departments and add Doctors");
        transaction.begin();

        Department cardiology = new Department("Cardiology", "Floor 3", "Dr. Mehta");
        Department neurology = new Department("Neurology", "Floor 4", "Dr. Gupta");

        Doctor doc1 = new Doctor("Dr. Mehta", "Cardiology", "LIC001");
        Doctor doc2 = new Doctor("Dr. Sharma", "Cardiology", "LIC002");
        Doctor doc3 = new Doctor("Dr. Gupta", "Neurology", "LIC003");
        Doctor doc4 = new Doctor("Dr. Singh", "Neurology", "LIC004");

        // Use helper method to maintain bidirectional relationship
        cardiology.addDoctor(doc1);
        cardiology.addDoctor(doc2);
        neurology.addDoctor(doc3);
        neurology.addDoctor(doc4);

        entityManager.persist(cardiology);
        entityManager.persist(neurology);
        transaction.commit();

        System.out.println("✓ Departments and Doctors created with bidirectional links");
        System.out.println("  Cardiology: " + cardiology);
        System.out.println("    Doctors: " + cardiology.getDoctors());
        System.out.println("  Neurology: " + neurology);
        System.out.println("    Doctors: " + neurology.getDoctors());

        assertNotNull(doc1.getDepartment());
        assertEquals("Cardiology", doc1.getDepartment().getName());
        assertEquals(2, cardiology.getDoctors().size());
    }

    /**
     * Step 2: Navigate from Department to Doctors (one-way).
     */
    @Test
    public void testNavigateDepartmentToDoctors() {
        System.out.println("Step 2: Navigate from Department to Doctors");

        transaction.begin();
        Department surgery = new Department("Surgery", "Floor 1", "Dr. Rao");
        Doctor doc1 = new Doctor("Dr. Rao", "General Surgery", "LIC101");
        Doctor doc2 = new Doctor("Dr. Khan", "Orthopedic Surgery", "LIC102");

        surgery.addDoctor(doc1);
        surgery.addDoctor(doc2);

        entityManager.persist(surgery);
        Long deptId = surgery.getId();
        transaction.commit();

        entityManager.clear();
        Department loadedDept = entityManager.find(Department.class, deptId);

        System.out.println("✓ Navigated: Department → Doctors");
        System.out.println("  Department: " + loadedDept.getName());
        System.out.println("  Doctors in this department:");
        for (Doctor doc : loadedDept.getDoctors()) {
            System.out.println("    - " + doc.getName());
        }

        assertEquals(2, loadedDept.getDoctors().size());
    }

    /**
     * Step 3: Navigate from Doctor to Department (other-way).
     */
    @Test
    public void testNavigateDoctorToDepartment() {
        System.out.println("Step 3: Navigate from Doctor to Department");

        transaction.begin();
        Department pediatrics = new Department("Pediatrics", "Floor 2", "Dr. Desai");
        Doctor doc = new Doctor("Dr. Desai", "Pediatrics", "LIC201");

        pediatrics.addDoctor(doc);
        entityManager.persist(pediatrics);
        Long docId = doc.getId();
        transaction.commit();

        entityManager.clear();
        Doctor loadedDoc = entityManager.find(Doctor.class, docId);

        System.out.println("✓ Navigated: Doctor → Department");
        System.out.println("  Doctor: " + loadedDoc.getName());
        System.out.println("  Department: " + loadedDoc.getDepartment().getName());

        assertEquals("Pediatrics", loadedDoc.getDepartment().getName());
    }

    /**
     * Step 4: Transfer Doctor between Departments.
     */
    @Test
    public void testTransferDoctorBetweenDepartments() {
        System.out.println("Step 4: Transfer Doctor between Departments");

        transaction.begin();
        Department ent = new Department("ENT", "Floor 5", "Dr. Verma");
        Department ortho = new Department("Orthopedics", "Floor 6", "Dr. Bhat");
        Doctor doc = new Doctor("Dr. Verma", "ENT", "LIC301");

        ent.addDoctor(doc);
        entityManager.persist(ent);
        entityManager.persist(ortho);
        Long docId = doc.getId();
        transaction.commit();

        // Transfer
        transaction.begin();
        Doctor loadedDoc = entityManager.find(Doctor.class, docId);
        Department loadedOrtho = entityManager.find(Department.class, ortho.getId());

        // Remove from ENT and add to Orthopedics
        loadedDoc.getDepartment().removeDoctor(loadedDoc);
        loadedOrtho.addDoctor(loadedDoc);

        transaction.commit();

        entityManager.clear();
        Doctor verifyDoc = entityManager.find(Doctor.class, docId);
        System.out.println("✓ Doctor transferred successfully");
        System.out.println("  Doctor: " + verifyDoc.getName());
        System.out.println("  New Department: " + verifyDoc.getDepartment().getName());

        assertEquals("Orthopedics", verifyDoc.getDepartment().getName());
    }

    /**
     * Step 5: JPQL Query to fetch doctors by department.
     */
    @Test
    public void testJPQLQueryDoctorsByDepartment() {
        System.out.println("Step 5: JPQL Query - Find Doctors by Department");

        transaction.begin();
        // Use unique department name to avoid data pollution from previous test runs
        String uniqueDeptName = "Oncology_" + System.nanoTime();
        Department oncology = new Department(uniqueDeptName, "Floor 7", "Dr. Nair");
        Doctor doc1 = new Doctor("Dr. Nair", "Medical Oncology", "LIC401");
        Doctor doc2 = new Doctor("Dr. Iyer", "Surgical Oncology", "LIC402");

        oncology.addDoctor(doc1);
        oncology.addDoctor(doc2);

        entityManager.persist(oncology);
        transaction.commit();

        String jpql = "SELECT d FROM Doctor d WHERE d.department.name = :dname";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("dname", uniqueDeptName);

        List<Doctor> doctors = query.getResultList();

        System.out.println("✓ JPQL Query executed: SELECT d FROM Doctor d WHERE d.department.name = '" + uniqueDeptName + "'");
        System.out.println("  Results:");
        for (Doctor doc : doctors) {
            System.out.println("    - " + doc.getName() + " (" + doc.getSpecialization() + ")");
        }

        assertEquals(2, doctors.size());
    }

    /**
     * Step 6: Remove Doctor from Department.
     */
    @Test
    public void testRemoveDoctorFromDepartment() {
        System.out.println("Step 6: Remove Doctor from Department");

        transaction.begin();
        Department gastro = new Department("Gastroenterology", "Floor 8", "Dr. Joshi");
        Doctor doc1 = new Doctor("Dr. Joshi", "Gastroenterology", "LIC501");
        Doctor doc2 = new Doctor("Dr. Pillai", "Gastroenterology", "LIC502");

        gastro.addDoctor(doc1);
        gastro.addDoctor(doc2);

        entityManager.persist(gastro);
        Long deptId = gastro.getId();
        Long doc1Id = doc1.getId();
        transaction.commit();

        // Remove doc1 from department (via cascade delete)
        transaction.begin();
        Department loadedGastro = entityManager.find(Department.class, deptId);
        Doctor loadedDoc1 = entityManager.find(Doctor.class, doc1Id);

        loadedGastro.removeDoctor(loadedDoc1);
        // Note: Since doctor has CascadeType.ALL and nullable=false,
        // we must delete the doctor to maintain referential integrity
        entityManager.remove(loadedDoc1);

        transaction.commit();

        entityManager.clear();
        Department verifyGastro = entityManager.find(Department.class, deptId);
        System.out.println("✓ Doctor removed from Department");
        System.out.println("  Department: " + verifyGastro.getName());
        System.out.println("  Remaining doctors: " + verifyGastro.getDoctors().size());

        assertEquals(1, verifyGastro.getDoctors().size());
    }

    /**
     * Step 7: Test importance of helper methods.
     */
    @Test
    public void testHelperMethodImportance() {
        System.out.println("Step 7: Demonstrate importance of helper methods");

        transaction.begin();

        Department pulmonology = new Department("Pulmonology", "Floor 9", "Dr. Mukerji");
        Doctor doc = new Doctor("Dr. Mukerji", "Respiratory Medicine", "LIC601");

        // WRONG WAY (without helper method):
        // pulmonology.getDoctors().add(doc);  // Only adds to department side
        // doc.setDepartment(pulmonology);      // Only sets doctor side
        // This could leave the relationship in an inconsistent state!

        // CORRECT WAY (with helper method):
        pulmonology.addDoctor(doc);  // Automatically handles both sides

        entityManager.persist(pulmonology);
        Long docId = doc.getId();
        transaction.commit();

        entityManager.clear();
        Doctor loadedDoc = entityManager.find(Doctor.class, docId);

        System.out.println("✓ Using helper methods ensures relationship consistency");
        System.out.println("  Doctor: " + loadedDoc.getName());
        System.out.println("  Department (via forward nav): " + loadedDoc.getDepartment().getName());
        System.out.println("  In Department.doctors list: " +
            loadedDoc.getDepartment().getDoctors().contains(loadedDoc));

        assertTrue(loadedDoc.getDepartment().getDoctors().contains(loadedDoc));
    }

    /**
     * Step 8: Report - explain mappedBy and bidirectional 1:N
     */
    @Test
    public void testReportBidirectional1N() {
        System.out.println("\nStep 8: REPORT - Bidirectional One-to-Many Mapping");
        System.out.println("=" .repeat(70));

        String report = """
        
        ════════════════════════════════════════════════════════════════════════
        TASK 2: Bidirectional One-to-Many Mapping (Department ↔ Doctor)
        ════════════════════════════════════════════════════════════════════════
        
        DATABASE SCHEMA:
        ┌─────────────────────┐          ┌──────────────────────┐
        │   departments       │          │     doctors          │
        ├─────────────────────┤          ├──────────────────────┤
        │ id (PK)             │◀─────────│ id (PK)               │
        │ name                │  1 : N   │ name                 │
        │ location            │          │ specialization       │
        │ head_doctor_name    │          │ license_no (UNIQUE)  │
        │ (no doctor ref)     │          │ department_id (FK)   │
        └─────────────────────┘          └──────────────────────┘
        
        KEY POINTS ABOUT BIDIRECTIONAL 1:N:
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        1. FK LOCATION & OWNERSHIP:
           • Foreign key (department_id) is in the MANY side table (doctors)
           • Doctor is the OWNING side - it holds the actual FK in database
           • Department is the INVERSE side - it uses mappedBy
           • @ManyToOne is always the FK owner
        
        2. ANNOTATIONS REQUIRED:
        
           Department.java (INVERSE side - ONE side):
           @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
           private List<Doctor> doctors = new ArrayList<>();
           
           Doctor.java (OWNING side - MANY side):
           @ManyToOne(fetch = FetchType.LAZY)
           @JoinColumn(name = "department_id")
           private Department department;
        
        3. WHAT IS "mappedBy"?
           • mappedBy = "department" → tells Hibernate to look for a field named
             "department" in the Doctor class
           • It's pointing to the OWNING side's field
           • NOT the database column name, NOT the entity class name
           • "department" is the FIELD NAME in Doctor class
        
        4. BIDIRECTIONAL NAVIGATION:
           • FORWARD works ✓: Department d → d.getDoctors() → List of Doctors
           • REVERSE works ✓: Doctor doc → doc.getDepartment() → Department
           • This is why it's called "bidirectional"
        
        5. HELPER METHODS ARE CRITICAL:
        
           Why needed?
           • JPA only persists ONE side of the relationship (owning side)
           • If you only add to Department.doctors list, the Doctor's department_id
             won't be set!
           • If you only set Doctor.department, the Doctor won't be in the list!
           
           Correct helper implementation:
           
           public void addDoctor(Doctor doctor) {
               if (!this.doctors.contains(doctor)) {
                   this.doctors.add(doctor);           // Sync collection side
                   doctor.setDepartment(this);         // Sync owning side
               }
           }
           
           public void removeDoctor(Doctor doctor) {
               if (this.doctors.contains(doctor)) {
                   this.doctors.remove(doctor);        // Sync collection side
                   doctor.setDepartment(null);         // Sync owning side
               }
           }
        
        6. COMMON MISTAKES:
        
           ✗ WRONG: Direct list manipulation
           Department d = new Department("Cardiology");
           Doctor doc = new Doctor("Dr. Smith");
           d.getDoctors().add(doc);              // Only sets Department side
           // doc.getDepartment() is still null! FK won't be set!
           
           ✓ CORRECT: Use helper method
           d.addDoctor(doc);                     // Handles both sides automatically
        
        7. CASCADE BEHAVIOR:
           @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
           
           • PERSIST: Saving Department persists all Doctors
           • REMOVE: Deleting Department deletes all Doctors
           • MERGE: Updating Department updates all Doctors
           • REFRESH: etc.
           
           ⚠️ Use with caution! Deleting a Department will cascade-delete all Doctors!
        
        8. FETCH STRATEGY:
           @OneToMany(..., fetch = FetchType.LAZY)
           
           • LAZY (recommended): Doctors list only loaded when accessed
           • EAGER: Doctors loaded immediately with Department
           • LAZY prevents N+1 query problem and memory bloat
        
        9. QUERYING BOTH DIRECTIONS:
        
           Find all doctors in Cardiology:
           Query q = em.createQuery(
               "SELECT d FROM Doctor d WHERE d.department.name = 'Cardiology'"
           );
           
           Find department of a specific doctor:
           Doctor doc = em.find(Doctor.class, 1L);
           Department dept = doc.getDepartment();
        
        10. BI 1:N vs UNI 1:N:
        
            BI 1:N (current - Department ↔ Doctor):
            • Department ← → Doctor (can navigate both ways)
            • More complex (requires helper methods)
            • More flexible queries possible
            • Default choice for most applications
            
            UNI 1:N (Doctor → Appointment in Task 3):
            • Doctor → Appointment (only one-way)
            • Simpler implementation
            • No helper methods needed
            • Limited query flexibility from Appointment side
        
        11. REAL-WORLD SCENARIOS:
        
            Use BI 1:N when:
            ✓ Need to find Department of a Doctor
            ✓ Need to list all Doctors in a Department
            ✓ Need to transfer Doctors between Departments
            ✓ Example: Hospital admin needs full bidirectional visibility
            
            Use UNI 1:N when:
            ✓ Only need one-direction navigation
            ✓ Reverse navigation not needed in domain logic
            ✓ Example: Doctor schedules Appointments, but don't need to
              find doctor from an appointment (it's implicitly known)
        
        ════════════════════════════════════════════════════════════════════════
        """;

        System.out.println(report);
    }
}



