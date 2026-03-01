package com.onetoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Util {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
		
	
	public void insertDetails() {
		
		//1. only aadhar
		AadharCard card = new AadharCard(1, "1234-5678-9012","Kavali", "2024-01-01");
		et.begin();
		em.persist(card);
		et.commit();
	
//		//2. only hostel room
		HostelRoom room = new HostelRoom(101, 12,"A Block", 2);
		et.begin();
		em.persist(room);
		et.commit();
		
		//3. student and associate
		Student student = new Student();
		student.setStudentId(1);
		student.setName("nani");
		student.setEmail("puneeth@gmail.com");
		student.setBranch("CSE");
		
		student.setAadharCard(card);
		student.setHostelRoom(room);
		
		et.begin();
		em.persist(student);
		et.commit();
//		
//		//4. details of student
		Student s = em.find(Student.class, 1);
		System.out.println(s.getName() + " " + s.getEmail() + " " + s.getAadharCard() + " " + s.getBranch());
		
		et.begin();
		em.remove(card); // this will throw a foreign key constrains Persistence exception if we want to remove this first we need to nullify in student then connection removes and then it will done
		et.commit();
		
		
		Student s1 = em.find(Student.class, 1);
		
		if(s1 != null) {
			et.begin();
			s1.setAadharCard(null);
			s1.setHostelRoom(null);
			
			em.remove(s1);
			
		}
		
		et.commit();
		
	}

	
}
