package feb12Assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // this is used to write non static methods rather than we need to write static
public class StudentDaoTest {
	
	private StudentDao dao;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeAll
	void init() {
		dao = new StudentDao();
		emf = Persistence.createEntityManagerFactory("postgres");
	}
	
	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
	}
	
	@AfterAll
	void closeEmf() {
		emf.close();
	}
	
	@AfterEach
	void closeEm() {		
		em.close();
	}
	
	@Test
	public void testSaveStudent() {
		Student s = new Student();
		s.setName("puneeth");
		s.setEmail("nani@gmail.com");
		s.setMarks(89);
		
		dao.saveStudent(s);
		Student saved = em.find(Student.class, s.getId());
		
		assertNotNull(saved);
		assertTrue(s.getId() > 0);
		assertEquals("puneeth", s.getName());
		assertEquals("nani@gmail.com", s.getEmail());
		assertEquals(89, s.getMarks());
		
	}
	
	@Test
	public void testMarksValidation() {

	    Student s = new Student();
	    s.setName("Invalid");
	    s.setEmail("invalid@gmail.com");
	    s.setMarks(150); 

	    assertThrows(IllegalArgumentException.class, () -> {
	        dao.saveStudent(s);
	    });
	}
	
	@Test
	public void testUpdateStudent() {
		Student s = new Student();
	    dao.updateStudent(3l, 88);

	    // Step 3: Fetch updated student
	    Student updated = dao.findStudentById(3l);

	    // Step 4: Assertions
	    assertNotNull(updated);
	    assertEquals("afterUpdate", updated.getName());
	}
	
	@Test
	public void testDeleteStudent() {
		dao.deleteStudent(1L);
		Student deleted = dao.findStudentById(1l);
		assertNull(deleted);
	}
	
	@Test
	public void testFindStudentById() {
		Student found = dao.findStudentById(1l);
		assertNotNull(found);
		assertEquals("puneeth", found.getName());
	}
	
	@Test
	public void testFindAllStudent() {
		List<Student> list = dao.findAll();
		assertNotNull(list);
		assertTrue(list.size() >= 1);
	}
	
	@Test
	public void testStudentCount() {
		List<Student> list = dao.findAll();
		assertTrue(list.size() >= 1);
	}
	
	@Test
	public void testFindStudentsWithMarksGreaterThan() {

	    Student s1 = new Student();
	    s1.setName("High1");
	    s1.setEmail("high1@gmail.com");
	    s1.setMarks(90);

	    Student s2 = new Student();
	    s2.setName("High2");
	    s2.setEmail("high2@gmail.com");
	    s2.setMarks(85);

	    Student s3 = new Student();
	    s3.setName("Low");
	    s3.setEmail("low@gmail.com");
	    s3.setMarks(60);

	    dao.saveStudent(s1);
	    dao.saveStudent(s2);
	    dao.saveStudent(s3);

	    List<Student> result = dao.findStudentsWithMarksGreaterThan(80);

	    assertNotNull(result);
	    assertTrue(result.size() >= 2);

	    for (Student s : result) {
	        assertTrue(s.getMarks() > 80);
	    }
	}

	
}
