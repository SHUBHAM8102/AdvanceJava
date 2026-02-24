package com.secondlevel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("postgres");

    public static void main(String[] args) {

       insert();   // Insert once
        read();     // Then test cache

        emf.close();  // Close at end
    }

    public static void insert() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Product p1 = new Product(1, "Book");
        Product p2 = new Product(2, "Pencil");

        em.persist(p1);
        em.persist(p2);

        tx.commit();
        em.close();
    }

    public static void read() {

        System.out.println("======= First Session =======");

        EntityManager em1 = emf.createEntityManager();
        Product p1 = em1.find(Product.class, 1);
        System.out.println(p1.getName());
        em1.close();

        System.out.println("======= Second Session =======");

        EntityManager em2 = emf.createEntityManager();
        Product p2 = em2.find(Product.class, 1);
        System.out.println(p2.getName());
        em2.close();
    }
}