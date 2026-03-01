package com.allinone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ServiceTest {

	@Test
	void testOneToOneMapping() {

	    InstructorProfile profile =
	        new InstructorProfile(201,"room1","999999");

	    Instructor instructor =
	        new Instructor(20,"test","IT");

	    instructor.setInstructorProfile(profile);

	    InstructorDAO dao = new InstructorDAO();
	    dao.saveInstructor(instructor);

	    Instructor fetched = dao.findInstructor(20);

	    assertNotNull(fetched);
	    assertNotNull(fetched.getInstructorProfile());
	}

	
	@Test
	void testOneToManyMapping(){

	    Course c1 = new Course(501,"Java",4);
	    Course c2 = new Course(502,"SQL",3);

	    Instructor inst = new Instructor(30,"multi","CS");
	    inst.setCourse(Arrays.asList(c1,c2));

	    InstructorDAO dao = new InstructorDAO();
	    dao.saveInstructor(inst);

	    Instructor fetched = dao.findInstructor(30);

	    assertEquals(2, fetched.getCourse().size());
	}

	
	@Test
	void testManyToOneMapping(){

	    Course course = new Course(700,"AI",5);

	    Enrollment e = new Enrollment(1,"A","s");
	    e.setCourse(course);

	    EnrollmentDao dao = new EnrollmentDao();
	    dao.saveEnrollment(e);

	    Enrollment fetched = dao.findEnrollment(1);

	    assertNotNull(fetched.getCourse());
	}

	
	@Test
	void testUpdateInstructor(){

	    InstructorDAO dao = new InstructorDAO();

	    Instructor inst = dao.findInstructor(1);
	    inst.setDepartment("UPDATED");

	    dao.updateInstructor(inst);

	    Instructor updated = dao.findInstructor(1);

	    assertEquals("UPDATED", updated.getDepartment());
	}

	
	@Test
	void testDeleteInstructor(){

	    InstructorDAO dao = new InstructorDAO();

	    dao.deleteInstructor(1);

	    Instructor inst = dao.findInstructor(1);

	    assertNull(inst);
	}

	
	
}
