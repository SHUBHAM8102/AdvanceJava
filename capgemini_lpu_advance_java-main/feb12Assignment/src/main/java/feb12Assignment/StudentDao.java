package feb12Assignment;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class StudentDao {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
	
	public void saveStudent(Student student) {
		
	    if (student.getMarks() < 0 || student.getMarks() > 100) {
	        throw new IllegalArgumentException("Marks must be between 0 and 100");
	    }
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		if(student != null) {
			et.begin();
			em.persist(student);
			et.commit();			
		}else {
			System.out.println("student is null");
		}
		
	}
	
	public void deleteStudent(long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Student student = em.find(Student.class, id);
		
		if(student != null) {
			et.begin();
			em.remove(student);
			et.commit();
		}else {
			System.out.println("student not found");
		}
		
		
	}
	
	public Student findStudentById(long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Student student = em.find(Student.class, id);
		return student;
	}
	
	public List<Student> findAll(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		List<Student> list = em.createQuery("select s from Student s", Student.class).getResultList();
		return list;
	}
	
	public void updateStudent(long id, int newMarks) {
		
		if (newMarks < 0 || newMarks > 100) {
	        throw new IllegalArgumentException("Marks must be between 0 and 100");
	    }
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Student student = em.find(Student.class, id);
		
		if(student != null) {
			et.begin();
			student.setMarks(newMarks);
			et.commit();
		}else {
			System.out.println("updated");
		}
		
	}
	
	public List<Student> findStudentsWithMarksGreaterThan(int marks){
		EntityManager em = emf.createEntityManager();
		String jpql = "select s from Student s where s.marks > :marks";
		List<Student> list = em.createQuery(jpql, Student.class).setParameter("marks", marks).getResultList();
		return list;
	}
	
}
