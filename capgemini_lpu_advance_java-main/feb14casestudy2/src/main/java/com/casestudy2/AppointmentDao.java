package com.casestudy2;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class AppointmentDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveAppointment(Appointment a) {
		et.begin();
		em.persist(a);
		et.commit();
	}
	
	public List<Appointment> findAppointmentByDoctor(Long id) {
		Doctor d = em.find(Doctor.class, id);
		
		if(d != null) {
			return d.getList();
		}
		return null;
		
	}
	
	public void updateFee(Long id, double newFee) {
		Appointment a = em.find(Appointment.class, id);
		
		if(a != null) {
			et.begin();
			a.setFees(newFee);
			et.commit();
		}
	}
	
	public Appointment findAppointment(Long id) {
		return em.find(Appointment.class, id);
	}
	
}
