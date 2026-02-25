package com.casestudy1;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class OrderDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveOrder(PurchaseOrder o) {
		if(o != null) {
			et.begin();
			em.persist(o);
			et.commit();
		}else {
			System.out.println("not found");
		}
	}
	
	public PurchaseOrder findOrder(Long id) {
		PurchaseOrder po = em.find(PurchaseOrder.class, id);
		if(po != null) {
			return po;
		}else {
			return null;
		}
	}
	
	public void findOrderByUser(Long userId) {
		User u = em.find(User.class, userId);
		if(u != null) {
			List<PurchaseOrder> list = u.getPurchaseOrder();
			System.out.println(list);
		}
	}
	
}
