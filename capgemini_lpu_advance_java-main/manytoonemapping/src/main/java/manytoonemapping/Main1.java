package manytoonemapping;

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
		
		String sql1 = "update employee set department_id = null where department_id=?1";
		String sql2 = "delete from dept where id=?1";
		
		Query updateQuery = em.createNativeQuery(sql1);
		Query deleteQuery = em.createNativeQuery(sql2);
		
		updateQuery.setParameter(1, 101);
		deleteQuery.setParameter(1, 101);
		et.begin();
		updateQuery.executeUpdate();
		deleteQuery.executeUpdate();
		et.commit();
		
	}
}
