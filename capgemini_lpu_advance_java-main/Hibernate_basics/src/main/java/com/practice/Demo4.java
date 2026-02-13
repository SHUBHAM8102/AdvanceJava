package com.practice;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Demo4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();//crud operation aree present in entity manager
		
		//Entity transaction interface  for commit
		//no need of commit in feteching data because commit is used where changes are made in table
		EntityTransaction et = em.getTransaction();
		
		String jpql="Slect s from Student s";
		Query query=em.createQuery(jpql);
		List<Student> list=query.getResultList();
		list.forEach(System.out::println);
		
		
	}

	

}
