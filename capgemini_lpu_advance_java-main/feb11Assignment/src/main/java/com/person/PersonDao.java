package com.person;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PersonDao {
	private EntityManagerFactory  emf = Persistence.createEntityManagerFactory("mysql");
	
//	
//	public void addPerson(Person person) {
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction et = em.getTransaction();
//		et.begin();
//		em.persist(person);
//		et.commit();
//	}
	
	
	
	
	public void deletePerson(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Person person = em.find(Person.class, id);
		
		if(person != null) {
			et.begin();
			em.remove(person);
			et.commit();
		}
		
		else {
			System.out.println("person not found");
		}
	}
//	
//	public void updatePersonById(int id, String newName) {
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction et = em.getTransaction();
//		
//		Person person = em.find(Person.class, id);
//		
//		if(person != null) {
//			et.begin();
//			person.setName(newName);
//			et.commit();
//		}
//		
//		else {
//			System.out.println("updated");
//		}
//		
//	}
//	
	public Person findPersonById(int id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Person person = em.find(Person.class, id);
		return person;
	}
//	
	public List<Person> findAll(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		List<Person> list = em.createQuery("select p from Person p", Person.class).getResultList();
		return list;
	}
//	
//	 public void close() {
//		 emf.close();
//	 }
	
}
