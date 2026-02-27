package pom.capgemini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pom.capgemini.entity.Course;

import java.util.List;

public class CourseDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ucrs_pu");

    public void saveCourse(Course course) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
        em.close();
    }

    public Course findCourse(int id) {
        EntityManager em = emf.createEntityManager();
        Course course = em.find(Course.class, id);
        em.close();
        return course;
    }

    public List<Course> findByInstructor(int instructorId) {
        EntityManager em = emf.createEntityManager();
        List<Course> courses = em.createQuery(
                "SELECT c FROM Instructor i JOIN i.courses c WHERE i.id = :id", Course.class)
                .setParameter("id", instructorId)
                .getResultList();
        em.close();
        return courses;
    }

}
