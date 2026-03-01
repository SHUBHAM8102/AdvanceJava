package com.prod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ProductDaoTest {
	
//	@BeforeEach
//	public void initEmf() {		
//	}
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
	EntityManager em = emf.createEntityManager();
	
	
	EntityTransaction et = em.getTransaction();
	
	
//	@Test
//	public void testAddProducts() {
//        ProductDao.addProducts(et, em);
//
//        Product p = em.find(Product.class, 1);
//
//        assertNotNull(p);
//        assertEquals("Oil", p.getName());
//        assertEquals(1000, p.getPrice());
//        assertEquals(3, p.getQuantity());
//    }

//    @Test
//    void testUpdateProducts() {
//
//        // First insert
//        ProductDao.addProducts(et, em);
//
//        // Then update
//        ProductDao.updateProducts(et, em);
//
//        Product p = em.find(Product.class, 1);
//
//        assertEquals("hero", p.getName());
//    }
//    
//    @Test
//    void testDeleteProducts() {
//
//        ProductDao.addProducts(et, em);
//
//        ProductDao.deleteProducts(et, em);
//
//        Product p = em.find(Product.class, 1);
//
//        assertNull(p);
//    }
    
//    @Test
//    void testFindById() {
//
//
//        Product p = em.find(Product.class, 1);
//
//        assertNotNull(p);
//        assertEquals(1, p.getId());
//    }
// 
//    @Test
//    void testFindAllProduct() {
//
//
//        long count = em.createQuery("select p from Product p", Product.class)
//                       .getResultList()
//                       .size();
//
//        assertTrue(count > 0);
//    }
	
}
