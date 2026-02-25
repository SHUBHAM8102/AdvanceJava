package com.mock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class UsersDao {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");

	public void insertData() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		User u1 = new User(1, "puneeth", 10000);
		User u2 = new User(2, "nani", 20000);
		User u3 = new User(3, "pavan", 30000);
		
		et.begin();
		
		em.persist(u1);
		em.persist(u2);
		em.persist(u3);
		
		et.commit();
	}
	
	public User findById(int id) {
		EntityManager em = emf.createEntityManager();
//		EntityTransaction et = em.getTransaction();
		
		return em.find(User.class, id);
		
	}

}
