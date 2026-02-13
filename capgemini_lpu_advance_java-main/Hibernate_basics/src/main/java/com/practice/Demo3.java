package com.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Demo3 {
//programe to update a column in table using primary key
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();//crud operation aree present in entity manager
		
		//Entity transaction interface  for commit
		//no need of commit in feteching data because commit is used where changes are made in table
		EntityTransaction et = em.getTransaction();
		Student student=em.find(Student.class,2);
		
		if(student!=null) {
			student.setPercentage(100);
			et.begin();
			em.merge(student);
			et.commit();
			emf.close();
		}

	}

}
