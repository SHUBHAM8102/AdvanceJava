package com.casestudy2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PatientDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void savePatient(Patient p) {
		if(p != null) {
			et.begin();
			em.persist(p);
			et.commit();
		}else {
			System.out.println("null");
		}
	}
	
	public Patient findPatient(Long id) {
		return em.find(Patient.class, id);
	}
	
	public void updatePatient(Patient p) {
		et.begin();
		em.merge(p);
		et.commit();
	}
	
	public void deletePatient(Long id) {
		Patient p = em.find(Patient.class, id);
		et.begin();
		em.remove(p);
		et.commit();
	}
}
