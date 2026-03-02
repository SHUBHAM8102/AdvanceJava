package onetomanymapping;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;



public class CollageDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void insertData() {
	
		
		Student s2 = new Student(108, "puneeth", "ECE");
		Student s3 = new Student(109, "pavan", "MEC");
		
		Collage c = new Collage();
		c.setId(2);
		c.setLocation("kavali");
		c.setEname("LPU");
		c.setPincode("929384");
		
		List<Student> list= new ArrayList<>();
		list.add(s3);
		list.add(s2);
		
		c.setStudent(list);
		
		et.begin();
		em.persist(c);
		em.persist(s2);
		em.persist(s3);
		et.commit();
	}
	
	public void deleteData() {
		Collage c = em.find(Collage.class, 1);
		et.begin();
		em.remove(c);
		et.commit();
	}
	
	public void updateData() {
	    Collage c = em.find(Collage.class, 1);   

	    if(c != null) {

	        et.begin();

	        c.setLocation("Delhi");
	        c.setPincode("110001");

	        et.commit();
	}
	

	    
	}

	
	public void findAllWithId() {
		Collage c = em.find(Collage.class, 1);
		List<Student> list = c.getStudent();
		System.out.println(list);
	}
		
}
