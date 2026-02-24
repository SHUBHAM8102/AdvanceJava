package manytoonemapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
	
		Department dept = new Department();
//		dept.setId(101);
		dept.setManager_name("Allen");
		dept.setName("HR");
		dept.setNo_of_emp(5);
		
		Employee e1 = new Employee("Miller", 109000, "ASE");
		e1.setDepartment(dept);
		
		Employee e2 = new Employee("virat", 200000, "Analyst");
		e2.setDepartment(dept);
		
		et.begin();
		em.persist(dept);
		em.persist(e1);
		em.persist(e2);
		et.commit();
		
	}
}
