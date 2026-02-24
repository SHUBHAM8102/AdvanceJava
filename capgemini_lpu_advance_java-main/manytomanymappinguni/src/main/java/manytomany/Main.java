package manytomany;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		List<Subject> sub = new ArrayList<>();
		
		Subject su1 = new Subject(101, "maths", 16);
		Subject su2 = new Subject(102, "science", 29);	
		Subject su3 = new Subject(103, "social", 99);
		Subject su4 = new Subject(104, "telugu", 69);

		sub.add(su1);
		sub.add(su2);
		
		List<Subject> sub1 = new ArrayList<>();
		sub1.add(su3);
		sub1.add(su4);
			
		Student s1 = new Student(1, "nani", "CSE", "MALE");
		s1.setSubject(sub);
		
		Student s2 = new Student(2, "puneeth", "ECE", "MALE");
		s2.setSubject(sub1);
		
		Student s3 = new Student(3, "Soumya", "MECH", "FEMALE");
		Student s4 = new Student(4, "Mohan", "CIVIL", "MALE");
		
		
		et.begin();


		em.persist(su1);
		em.persist(su2);
		em.persist(su3);
		em.persist(su4);

	
		em.persist(s1);
		em.persist(s2);
		em.persist(s3);
		em.persist(s4);

		et.commit();
		
	}
}
