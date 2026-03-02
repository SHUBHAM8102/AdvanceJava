package onetomanymapping;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class StudentDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();

	public void insertData() {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Student s1 = new Student();
		s1.setId(101);
		s1.setBranch("CSE");
		s1.setEname("nani");
		
		Student s2 = new Student(102, "puneeth", "ECE");
		Student s3 = new Student(103, "pavan", "MEC");
		
		et.begin();
		em.persist(s1);
		em.persist(s2);
		em.persist(s3);
		et.commit();
	}
	
	public void updateStudentData() {
		Collage c = em.find(Collage.class, 1);
		List<Student> list = c.getStudent();
		Student s = new Student();
		s.setId(105);
		s.setEname("nani");
		s.setBranch("IT");
		
		list.add(s);
		
		c.setStudent(list);
		
		et.begin();
		em.persist(s);
		et.commit();
		
	}
	
	public void updateStudent() {

	    Student s = em.find(Student.class, 101);

	    if(s != null) {

	        et.begin();
	        s.setBranch("AI");
	        et.commit();
	    }
	}

	
	public void deleteStudent() {

	    Student s = em.find(Student.class, 102);

	    if (s != null) {

	    	et.begin();
	    	
	    	String sql1 = "delete from collage_student where student_id=?1";
	        Query query = em.createNativeQuery(sql1);
	        query.setParameter(1, s.getId());
	        
	        query.executeUpdate();

	        String sql2 = "delete from student where id=?1";
	        Query query1 = em.createNativeQuery(sql2);
	        query1.setParameter(1, s.getId());
	        
	        query1.executeUpdate();
	        
	        et.commit();
	        
	    }
	}
	
	public void findById() {
		Student s = em.find(Student.class, 102);
		if(s != null) {
			System.out.println(s.getEname() + s.getBranch());
		}
	}


	
}
