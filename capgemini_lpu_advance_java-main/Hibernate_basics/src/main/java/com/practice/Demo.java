package com.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Demo {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();//crud operation aree present in entity manager
		
		//Entity transaction interface  for commit
		
		EntityTransaction et = em.getTransaction();
		
		Student s=new Student();
		s.setId(3);
		s.setName("puspshit");
		s.setPercentage(50.5);
		et.begin();
		em.persist(s);
		et.commit();
		
		emf.close();
	}

}
