package bidirect;

import java.util.Arrays;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class User {
	
	
	
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Collage c = new Collage();
		c.setId(1);
		c.setEname("nani");
		c.setPincode("kvl");
		
		Student s1 = new Student(101, "nani", "CSE");
		
		Student s2 = new Student();
		s2.setId(102);
		s2.setEname("Kiran");
		s2.setBranch("ECE");
		
		s1.setCollage(c);
		s2.setCollage(c);
		
		c.setStudent(Arrays.asList(s1, s2));
		
		et.begin();
		em.persist(c);
		//here we are saving collage obj that's y put cascase in collage class
		/* College (parent)
		   		↓
		Students (children) */
		// parent save immediately child also saved that's y put cascade in collage
		et.commit();

	}
	
	
}
