package com.casestudy2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DoctorDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveDoctor(Doctor d) {
		if(d != null) {
			et.begin();
			em.persist(d);
			et.commit();
		}else {
			System.out.println("null");
		}
	}
	
	public Doctor findDoctor(Long id) {
		return em.find(Doctor.class, id);
	}
}
