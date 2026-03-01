package com.allinone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Service {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	
	public static void main(String[] args) {
//		Create instructor with profile
		Instructor i1 = new Instructor(1, "nani", "it");
		Instructor i2 = new Instructor(2, "puneeth", "hr");
		
		InstructorProfile ip1 = new InstructorProfile(101, "nellore", "9283747474");
		InstructorProfile ip2 = new InstructorProfile(102, "kavali", "98276347823");
		
		i1.setInstructorProfile(ip1);
		i2.setInstructorProfile(ip2);
		
		InstructorDAO id = new InstructorDAO();
		id.saveInstructor(i1);
		id.saveInstructor(i2);
		
		
//		Add multiple courses to instructor
		Course c1 = new Course(1001, "maths", 4);
		Course c2 = new Course(1002, "ps", 3);
		Course c3 = new Course(1003, "science", 2);
		
		List<Course> list = new ArrayList<>(Arrays.asList(c1, c2, c3));
		
		Instructor i3 = new Instructor(3, "rahul", "science");
		i3.setCourse(list);
	
		InstructorDAO id1 = new InstructorDAO();
		id1.saveInstructor(i3);
		
		
//		 Add enrollment to course
		Enrollment e1 = new Enrollment(10001, "2", "A+");
		Enrollment e2 = new Enrollment(10002, "4", "B+");
		
		Course c4 = new Course(1004, "social", 3);
		e1.setCourse(c4);
		e2.setCourse(c4);
		
		EnrollmentDao ed = new EnrollmentDao();
		ed.saveEnrollment(e1);
		ed.saveEnrollment(e2);
		
//		Update enrollment grade
		
		ed.updateGrade(e2, "D");
		
//		Fetch instructor with courses
		
		CourseDAO dao = new CourseDAO();
		Instructor inst = dao.findInstructorWithCourses(3);

		System.out.println(inst);
		System.out.println(inst.getCourse());

//		delete instructor
		
		InstructorDAO dao1 = new InstructorDAO();

		dao1.deleteInstructor(3);
		System.out.println("Instructor deleted");
		// because of cascade childrens are also deleted
		
		
	}
	
}
