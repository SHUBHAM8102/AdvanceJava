package manytomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class Main1 {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		String sql1 = "delete from student_subject where subject_id=?1";
		String sql2 = "delete from subject where id=?1";
		
		Query query1 = em.createNativeQuery(sql1);
		query1.setParameter(1, 101);
		Query query2 = em.createNativeQuery(sql2);
		query2.setParameter(1, 101);
		
		et.begin();
		query1.executeUpdate();
		query2.executeUpdate();
		et.commit();
		
		
	}
}
