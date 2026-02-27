package com.fintech.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {

    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            
            entityManagerFactory = Persistence.createEntityManagerFactory("fintechPU");
            System.out.println("EntityManagerFactory initialized successfully.");
        } catch (Exception e) {
            System.err.println("Failed to initialize EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            throw new IllegalStateException("EntityManagerFactory is not initialized or has been closed");
        }
        return entityManagerFactory.createEntityManager();
    }

    
    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory closed successfully.");
        }
    }

    
    public static boolean isOpen() {
        return entityManagerFactory != null && entityManagerFactory.isOpen();
    }
}

