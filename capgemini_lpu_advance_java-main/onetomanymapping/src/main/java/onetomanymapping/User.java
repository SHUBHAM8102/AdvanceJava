package onetomanymapping;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class User {
	
	
	
	public static void main(String[] args) {
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
//		EntityManager em = emf.createEntityManager();
//		EntityTransaction et = em.getTransaction();
//
//		Student s1 = new Student();
//		s1.setId(101);
//		s1.setBranch("CSE");
//		s1.setEname("nani");
//		
//		Student s2 = new Student(102, "puneeth", "ECE");
//		Student s3 = new Student(103, "pavan", "MEC");
//		
//		Collage c = new Collage();
//		c.setId(1);
//		c.setLocation("kavali");
//		c.setEname("LPU");
//		c.setPincode("929384");
//		
//		List<Student> list= new ArrayList<>();
//		list.add(s1);
//		list.add(s2);
//		
//		c.setStudent(list);
//		
//		et.begin();
//		em.persist(c);
//		em.persist(s1);
//		em.persist(s2);
//		em.persist(s3);
//		et.commit();
//		
		
		StudentDao sd = new StudentDao();
//		sd.updateStudentData();
		sd.deleteStudent();
//		sd.findById();
		
//		CollageDao cd = new CollageDao();
//		cd.insertData();
		
	}
	
	
}
