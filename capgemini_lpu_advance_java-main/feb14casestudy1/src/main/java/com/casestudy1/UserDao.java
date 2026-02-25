package com.casestudy1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class UserDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveUser(User u) {
		
		if(u != null) {			
			et.begin();
			em.persist(u);
			et.commit();
		}else {
			System.out.println("null");
		}
	}
	
	public User findUser(Long id) {
		User u = em.find(User.class, id);
		if(u == null) {
			return null;
		}
		return u;
	}
	
	public void deleteUser(Long id) {
		User u = em.find(User.class, id);
		
		if(u != null) {
			et.begin();
			em.remove(u);
			et.commit();
		}
		
	}
	
	public void updateUser(User u) {
		et.begin();
		em.merge(u);
		et.commit();
		
	}
	
}
