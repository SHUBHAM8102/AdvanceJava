package com.basics_hibernate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.product.Product;
import com.product.ProductDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// ctrl+shift+o --> import shortcut

public class ProductDaoTest {

	
	//EMF
	//EM
	//ET
	static EntityManagerFactory emf;
	
	static EntityManager em;
	
	@BeforeAll
	public static void initEmf() {
		System.out.println("Init EMF");
		emf = Persistence.createEntityManagerFactory("postgres");
	}
	
	
	@BeforeEach
	public void initEm() {
		em=emf.createEntityManager();
	}
	
	
	@Test
	public static void insertProductTest() {
		System.out.println("Product Inserted");
		ProductDao dao=new ProductDao();
		Product product=new Product();
		product.setId(6);
		product.setName("Pencil");
		product.setQuantity(5);
		dao.insertProduct(product);
	}
	
	@Test
	public void insertProductTestNull() {
		ProductDao dao=new ProductDao();
		//String actualRes=dao.insertProduct(null);
		//assertEquals("Illegal Argument",actualRes);
	}
	
	
	@Test
	public void findById() {
		System.out.println("Product Find");
		ProductDao dao=new ProductDao();
		Product p=dao.findById(2);
		assertNotNull(p);
	}
//	
	
	@AfterEach
	public static void destroyEm() {
		emf.close();
	}
	
	
	@AfterAll
	public static void destroyEmf() {
		emf.close();
	}
	
}