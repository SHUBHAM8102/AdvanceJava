package com.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;


public class ProductDao {
	 EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
	 
	 
	 EntityManager em=emf.createEntityManager();

	public void insertProduct(Product p) {
		EntityTransaction et=em.getTransaction();
		
		et.begin();
		em.persist(p);
		et.commit();
	}
	
	public Product findById(int id) {
		
		Product p=em.find(Product.class, id);
		return p;
	}
	
	public void update(Product p){
        EntityTransaction et = em.getTransaction();

        et.begin();
        em.merge(p);
        et.commit();
        em.close();
    }
	
	public void deleteById(int id) {
		Product p=findById(id);
		if(p!=null) {
			EntityTransaction et=em.getTransaction();
			et.begin();
			em.remove(p);
			et.commit();
		}else {
			System.out.println("Data Not Exist");
		}
		
	}

}