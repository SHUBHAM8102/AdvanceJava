package com.casestudy1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PaymentDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void savePayment(Payment p) {
		if(p != null) {			
			et.begin();
			em.persist(p);
			et.commit();
		}else {
			System.out.println("null");
		}
	}
	
	public Payment findPayment(Long id) {
		return em.find(Payment.class, id);
		
	}
	
}
