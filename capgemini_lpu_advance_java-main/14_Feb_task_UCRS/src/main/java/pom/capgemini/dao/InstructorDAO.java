package pom.capgemini.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pom.capgemini.entity.Instructor;

public class InstructorDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ucrs_pu");

    public void saveInstructor(Instructor instructor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(instructor);
        em.getTransaction().commit();
        em.close();
    }

    public Instructor findInstructor(int id) {
        EntityManager em = emf.createEntityManager();
        Instructor instructor = em.find(Instructor.class, id);
        em.close();
        return instructor;
    }

    public void updateInstructor(Instructor instructor) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(instructor);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteInstructor(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Instructor instructor = em.find(Instructor.class, id);
        if (instructor != null) {
            em.remove(instructor);
        }
        em.getTransaction().commit();
        em.close();
    }
}

