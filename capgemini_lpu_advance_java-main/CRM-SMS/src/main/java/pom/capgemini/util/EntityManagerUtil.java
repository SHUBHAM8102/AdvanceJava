package pom.capgemini.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for EntityManager management and database utilities
 */
public class EntityManagerUtil {

    private static EntityManagerFactory emf = null;

    /**
     * Get or create EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("CRM_PU");
        }
        return emf;
    }

    /**
     * Get a new EntityManager instance
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /**
     * Close EntityManagerFactory
     */
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    /**
     * Check if EntityManager is valid and open
     */
    public static boolean isEntityManagerValid(EntityManager em) {
        return em != null && em.isOpen();
    }

    /**
     * Execute a query with automatic transaction handling
     */
    public static void executeInTransaction(EntityManager em, TransactionCallback callback) {
        try {
            em.getTransaction().begin();
            callback.execute(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        }
    }

    /**
     * Functional interface for transaction callback
     */
    @FunctionalInterface
    public interface TransactionCallback {
        void execute(EntityManager em) throws Exception;
    }
}

