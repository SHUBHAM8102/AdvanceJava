package com.practice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
//programe to fetch data 
public class Demo1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();//crud operation aree present in entity manager
		
		//Entity transaction interface  for commit
		//no need of commit in feteching data because commit is used where changes are made in table
		EntityTransaction et = em.getTransaction();
		Student student=em.find(Student.class,1 );
		System.out.println(student.getId()+" "+student.getName()+" "+student.getPercentage());

	}//to fectch all the details we need to write custom querries

}
