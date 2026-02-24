package crm.service;

import com.Crm.Support_Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class TicketService {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

	public void raiseTicket(Support_Ticket ticket) {
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction et = em.getTransaction();

	    try {
	        et.begin();
	        em.persist(ticket);
	        et.commit();
	    } catch (Exception e) {
	        if (et.isActive()) et.rollback();
	    } finally {
	        em.close();
	    }
	}

}
