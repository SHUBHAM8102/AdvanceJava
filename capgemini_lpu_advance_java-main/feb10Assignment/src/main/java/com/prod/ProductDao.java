package com.prod;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class ProductDao {
	
	public static void addProducts(EntityTransaction et, EntityManager em) {
		Product p = new Product();
		p.setId(1);
		p.setName("Oil");
		p.setPrice(1000);
		p.setQuantity(3);
		
		et.begin();
		em.persist(p);
		et.commit();
		
	}
	
	public static void updateProducts(EntityTransaction et, EntityManager em) {
		Product product = em.find(Product.class, 1);
		
		if(product != null) {
			product.setName("hero");
			em.merge(product);
			et.begin();
			et.commit();
		}
			
	}
	
	public static void deleteProducts(EntityTransaction et, EntityManager em) {
		Product p = em.find(Product.class, 1);
		
		if(p != null) {			
			et.begin();
			em.remove(p);
			et.commit();
		}
		
	}
	
	public static void findById(EntityTransaction et, EntityManager em) {
		Product p = em.find(Product.class, 1);
		System.out.println(p.getId() + " " + p.getName() + " " + p.getPrice());
	}
	
	public static void findAllProduct(EntityTransaction et, EntityManager em) {
		String jpql = "select p from Product p";
		Query query = em.createQuery(jpql);
		
		List<Product> list = query.getResultList();
		list.forEach(System.out::println);
		
	}
	
}


