package com.secondlevel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Main {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	
	public static void main(String[] args) {
		read();
//		insert();
	}
	
	public static void insert() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Product p1 = new Product(1, "Book");
		Product p2 = new Product(2, "Pencil");
		
		et.begin();
		em.persist(p1);
		em.persist(p2);
		et.commit();
		
	}
	
public static void read() {

    System.out.println("=== SECOND LEVEL CACHE TEST ===");

    // FIRST ENTITY MANAGER → DB HIT
    EntityManager em1 = emf.createEntityManager();
    System.out.println("Fetching Product first time (DB expected)");
    Product p1 = em1.find(Product.class, 1);
    System.out.println(p1);
    em1.close();


    System.out.println("-------------------------------");

    // SECOND ENTITY MANAGER → SHOULD COME FROM L2 CACHE
    EntityManager em2 = emf.createEntityManager();
    System.out.println("Fetching Product second time (should use L2 cache)");
    Product p2 = em2.find(Product.class, 1);
    System.out.println(p2);
    em2.close();
}
	
}