package com.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonDaoTest {
	
	private PersonDao dao;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeAll
	void setUp() {
		dao = new PersonDao();
		emf = Persistence.createEntityManagerFactory("mysql");
		em = emf.createEntityManager();
	}
	
//	@AfterEach
//	void cleanUp() {
//		EntityTransaction et = em.getTransaction();
//		et.begin();
//		em.createQuery("delete from Person").executeUpdate();
//		et.commit();
//	}
	
	 @AfterAll
	 void close() {
		 em.close();
		 emf.close();
	 }
	 
//	 @Test
//	 void testAddPerson() {
//		 Person p = new Person();
//		 p.setId(1);
//		 p.setName("nani");
//		 p.setDob("2005-01-01");
//		 p.setPincode("234554");
//		 p.setAddress("kavali");
//		 
//		 dao.addPerson(p);
//		 
//		 Person saved = em.find(Person.class, p.getId());
//		 
//		 assertNotNull(saved);
//		 assertTrue(p.getId() > 0);
//		 assertEquals("nani", saved.getName());
//		 assertEquals("kavali",p.getAddress());
//		 assertEquals("234554", p.getPincode());
//	 }
	 
	 
	 @Test
	 public void deletePersonTest() {
		    dao.deletePerson(1);
		    Person deleted = dao.findPersonById(1);
		    assertNull(deleted);
	 }
	 
	 @Test
	 void testFindPersonById() {

	     Person found = dao.findPersonById(1);

	     assertNotNull(found);
	     assertEquals("findme", found.getName());
	 }
	 
	 @Test
	 void testFindAllWithData() {


	     // Act
	     List<Person> list = dao.findAll();

	     // Assert
	     assertNotNull(list);
	     assertTrue(list.size() >= 2);
	 }

	
	
}
