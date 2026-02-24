package crm.service;

import com.Crm.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ProductService {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

    public void createProduct(Product product) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
        	et.begin();
            em.persist(product);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
        } finally {
            em.close();
        }
    }
}
