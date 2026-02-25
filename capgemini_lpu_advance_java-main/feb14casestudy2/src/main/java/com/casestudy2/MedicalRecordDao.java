package com.casestudy2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class MedicalRecordDao {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("mysql");

    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();


    public void saveMedicalRecord(MedicalRecord m){

        if(m != null){
            et.begin();
            em.persist(m);
            et.commit();
        } else{
            System.out.println("Medical record is null");
        }
    }


    public MedicalRecord findMedicalRecord(int id){

        return em.find(MedicalRecord.class, id);
    }


    public void updateMedicalRecord(MedicalRecord m){

        if(m != null){
            et.begin();
            em.merge(m);
            et.commit();
        }
    }


    public void deleteMedicalRecord(int id){

        MedicalRecord m = em.find(MedicalRecord.class,id);

        if(m != null){
            et.begin();
            em.remove(m);
            et.commit();
        }
    }
}
