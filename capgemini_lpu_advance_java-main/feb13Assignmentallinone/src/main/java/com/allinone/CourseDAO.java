package com.allinone;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class CourseDAO {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveCourse(Course c) {
		et.begin();
		em.persist(c);
		et.commit();
	}
	
	public Course findCourse(int id) {
		return em.find(Course.class, id);
	}
	
	public Instructor findInstructorWithCourses(int c_id){
	    return em.find(Instructor.class, c_id);
	}

	
}
