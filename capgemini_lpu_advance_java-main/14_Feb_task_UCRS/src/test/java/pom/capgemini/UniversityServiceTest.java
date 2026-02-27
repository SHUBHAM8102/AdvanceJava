package pom.capgemini;

import org.junit.jupiter.api.*;
import pom.capgemini.entity.Course;
import pom.capgemini.entity.Enrollment;
import pom.capgemini.entity.Instructor;
import pom.capgemini.entity.InstructorProfile;
import pom.capgemini.service.UniversityService;

import java.util.Arrays;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UniversityServiceTest {

    static UniversityService service = new UniversityService();
    static int instructorId;
    static int courseId;
    static int enrollmentId;

    @Test
    @Order(1)
    void testOneToOneMapping() {
        Instructor instructor = service.createInstructorWithProfile("Dr. Sharma", "IT", "Room 201", "1234567890");
        instructorId = instructor.getId();
        Instructor fetched = service.findInstructor(instructorId);
        Assertions.assertNotNull(fetched);
        Assertions.assertNotNull(fetched.getInstructorProfile());
        Assertions.assertEquals("Room 201", fetched.getInstructorProfile().getOfficeRoom());
        Assertions.assertEquals("1234567890", fetched.getInstructorProfile().getPhone());
    }

    @Test
    @Order(2)
    void testOneToManyMapping() {
        Course c1 = new Course("Data Structures", 4);
        Course c2 = new Course("Algorithms", 3);
        service.addCoursesToInstructor(instructorId, Arrays.asList(c1, c2));
        Instructor fetched = service.fetchInstructorWithCourses(instructorId);
        Assertions.assertNotNull(fetched.getCourses());
        Assertions.assertEquals(2, fetched.getCourses().size());
        courseId = fetched.getCourses().get(0).getId();
    }

    @Test
    @Order(3)
    void testManyToOneMapping() {
        Enrollment enrollment = service.addEnrollmentToCourse(courseId, "Fall 2025", "B+");
        enrollmentId = enrollment.getId();
        Enrollment fetched = service.findEnrollment(enrollmentId);
        Assertions.assertNotNull(fetched);
        Assertions.assertNotNull(fetched.getCourse());
        Assertions.assertEquals(courseId, fetched.getCourse().getId());
    }

    @Test
    @Order(4)
    void testDAOUpdate() {
        service.updateEnrollmentGrade(enrollmentId, "A");
        Enrollment fetched = service.findEnrollment(enrollmentId);
        Assertions.assertEquals("A", fetched.getGrade());
    }

    @Test
    @Order(5)
    void testDAODelete() {
        service.deleteInstructor(instructorId);
        Instructor fetched = service.findInstructor(instructorId);
        Assertions.assertNull(fetched);
    }

    @Test
    @Order(6)
    void testFullServiceIntegration() {
        Instructor instructor = service.createInstructorWithProfile("Dr. Verma", "ECE", "Room 301", "9999999999");
        int id = instructor.getId();

        Course c1 = new Course("VLSI", 4);
        Course c2 = new Course("Signals", 3);
        service.addCoursesToInstructor(id, Arrays.asList(c1, c2));

        Instructor fetched = service.fetchInstructorWithCourses(id);
        Assertions.assertEquals(2, fetched.getCourses().size());

        int cId = fetched.getCourses().get(0).getId();
        Enrollment enrollment = service.addEnrollmentToCourse(cId, "Spring 2026", "B");
        service.updateEnrollmentGrade(enrollment.getId(), "A+");
        Enrollment updatedEnrollment = service.findEnrollment(enrollment.getId());
        Assertions.assertEquals("A+", updatedEnrollment.getGrade());

        service.deleteInstructor(id);
        Assertions.assertNull(service.findInstructor(id));
    }
}

