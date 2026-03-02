package bidirect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Passport p = new Passport();
		p.setId(101);
		p.setName("miller");
		p.setCitizen("indian");
		p.setDob("26/03/2009");
		
		Person pe = new Person();
		pe.setId(1);
		pe.setName("allen");
		pe.setPassport(p);
		p.setPerson(pe);
		
		et.begin();
		em.persist(pe);
		et.commit();
		
		
	}
}
