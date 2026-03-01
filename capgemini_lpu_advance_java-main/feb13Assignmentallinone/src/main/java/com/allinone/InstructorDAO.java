package com.allinone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class InstructorDAO {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveInstructor(Instructor i) {
		et.begin();
		em.persist(i);
		et.commit();
	}
	
	
	public Instructor findInstructor(int id) {
		return em.find(Instructor.class, id);
	}
	
	public void updateInstructor(Instructor i) {
		et.begin();
		Instructor existing = em.find(Instructor.class, i.getId());
	    if(existing != null) {
	        em.merge(i);
	    }
		et.commit();
	}
	
	public void deleteInstructor(int id){
	    et.begin();

	    Instructor inst = em.find(Instructor.class, id);
	    if(inst != null){
	        em.remove(inst);
	    }

	    et.commit();
	}

	
}
