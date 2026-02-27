package pom.capgemini.service;

import pom.capgemini.dao.CourseDAO;
import pom.capgemini.dao.EnrollmentDAO;
import pom.capgemini.dao.InstructorDAO;
import pom.capgemini.entity.Course;
import pom.capgemini.entity.Enrollment;
import pom.capgemini.entity.Instructor;
import pom.capgemini.entity.InstructorProfile;

import java.util.List;

public class UniversityService {

    private InstructorDAO instructorDAO = new InstructorDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public Instructor createInstructorWithProfile(String name, String department, String officeRoom, String phone) {
        InstructorProfile profile = new InstructorProfile(officeRoom, phone);
        Instructor instructor = new Instructor(name, department);
        instructor.setInstructorProfile(profile);
        instructorDAO.saveInstructor(instructor);
        return instructor;
    }

    public Instructor addCoursesToInstructor(int instructorId, List<Course> courses) {
        Instructor instructor = instructorDAO.findInstructor(instructorId);
        instructor.getCourses().addAll(courses);
        instructorDAO.updateInstructor(instructor);
        return instructor;
    }

    public Enrollment addEnrollmentToCourse(int courseId, String semester, String grade) {
        Course course = courseDAO.findCourse(courseId);
        Enrollment enrollment = new Enrollment(semester, grade);
        enrollment.setCourse(course);
        enrollmentDAO.saveEnrollment(enrollment);
        return enrollment;
    }

    public void updateEnrollmentGrade(int enrollmentId, String newGrade) {
        enrollmentDAO.updateGrade(enrollmentId, newGrade);
    }

    public Instructor fetchInstructorWithCourses(int instructorId) {
        Instructor instructor = instructorDAO.findInstructor(instructorId);
        instructor.getCourses().size();
        return instructor;
    }

    public void deleteInstructor(int instructorId) {
        instructorDAO.deleteInstructor(instructorId);
    }

    public Instructor findInstructor(int id) {
        return instructorDAO.findInstructor(id);
    }

    public Enrollment findEnrollment(int id) {
        return enrollmentDAO.findEnrollment(id);
    }

    public Course findCourse(int id) {
        return courseDAO.findCourse(id);
    }

    public List<Course> findCoursesByInstructor(int instructorId) {
        return courseDAO.findByInstructor(instructorId);
    }
}

