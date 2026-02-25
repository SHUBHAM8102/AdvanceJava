package com.casestudy1;


import jakarta.persistence.*;

public class ProfileDao {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("mysql");

    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void saveProfile(Profile p){

        et.begin();
        em.persist(p);
        et.commit();
    }
}

