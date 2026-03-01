package com.allinone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class EnrollmentDao {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	public void saveEnrollment(Enrollment e) {
		et.begin();
		em.persist(e);
		et.commit();
	}
	
	public Enrollment findEnrollment(int id) {
		return em.find(Enrollment.class, id);
	}
	
	public void updateGrade(Enrollment e, String grade) {
		String sql = "update enrollment set grade=?1 where id=?2";
		
		Query query = em.createNativeQuery(sql);
		
		query.setParameter(1, grade);
		query.setParameter(2, e.getId());
		
		et.begin();
		query.executeUpdate();
		et.commit();
		
	}
	
}
