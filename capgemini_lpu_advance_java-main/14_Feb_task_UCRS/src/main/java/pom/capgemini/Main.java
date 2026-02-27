package pom.capgemini;

import pom.capgemini.entity.Course;
import pom.capgemini.entity.Instructor;
import pom.capgemini.entity.Enrollment;
import pom.capgemini.service.UniversityService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        UniversityService service = new UniversityService();

        Instructor instructor = service.createInstructorWithProfile("Dr. Smith", "CS", "Room 101", "9876543210");
        System.out.println("Instructor created with ID: " + instructor.getId());

        Course c1 = new Course("Java", 4);
        Course c2 = new Course("Python", 3);
        service.addCoursesToInstructor(instructor.getId(), Arrays.asList(c1, c2));
        System.out.println("Courses added to instructor");

        Instructor fetched = service.fetchInstructorWithCourses(instructor.getId());
        System.out.println("Instructor: " + fetched.getName() + ", Courses: " + fetched.getCourses().size());

        Enrollment enrollment = service.addEnrollmentToCourse(c1.getId(), "Spring 2025", "A");
        System.out.println("Enrollment created with ID: " + enrollment.getId());

        service.updateEnrollmentGrade(enrollment.getId(), "A+");
        System.out.println("Grade updated");
    }
}