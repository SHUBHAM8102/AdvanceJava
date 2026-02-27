package pom.capgemini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pom.capgemini.entity.Enrollment;

public class EnrollmentDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ucrs_pu");

    public void saveEnrollment(Enrollment enrollment) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(enrollment);
        em.getTransaction().commit();
        em.close();
    }

    public Enrollment findEnrollment(int id) {
        EntityManager em = emf.createEntityManager();
        Enrollment enrollment = em.find(Enrollment.class, id);
        em.close();
        return enrollment;
    }

    public void updateGrade(int enrollmentId, String newGrade) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Enrollment enrollment = em.find(Enrollment.class, enrollmentId);
        if (enrollment != null) {
            enrollment.setGrade(newGrade);
            em.merge(enrollment);
        }
        em.getTransaction().commit();
        em.close();
    }
}

