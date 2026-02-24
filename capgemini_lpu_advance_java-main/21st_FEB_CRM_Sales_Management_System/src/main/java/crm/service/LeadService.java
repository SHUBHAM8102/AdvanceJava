package crm.service;

import com.Crm.Customer;
import com.Crm.Lead;
import com.Crm.Order;
import com.Crm.SalesEmployee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class LeadService {

	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

	    public void createLead(Lead lead) {
	        executeTransaction(em -> em.persist(lead));
	    }

	    public void assignLead(Long leadId, SalesEmployee employee) {
	        executeTransaction(em -> {
	            Lead lead = em.find(Lead.class, leadId);
	            if (lead != null) {
	                lead.setSalesEmployee(employee);
	                em.merge(lead);
	            }
	        });
	    }

	    public void convertLeadToOrder(Long leadId, Order order) {
	        executeTransaction(em -> {
	            Lead lead = em.find(Lead.class, leadId);
	            if (lead != null) {
	                order.setLead(lead);
	                em.persist(order);
	                lead.setStatus("Converted");
	            }
	        });
	    }

	    private void executeTransaction(java.util.function.Consumer<EntityManager> action) {
	        EntityManager em = emf.createEntityManager();
	        EntityTransaction et = em.getTransaction();

	        try {
	        	et.begin();
	            action.accept(em);
	            et.commit();
	        } catch (Exception e) {
	            if (et.isActive()) et.rollback();
	        } finally {
	            em.close();
	        }
	    }
	    public void assignLeadToEmployee(Long leadId, Long empId) {

	        EntityManager em = emf.createEntityManager();
	        EntityTransaction tx = em.getTransaction();

	        try {
	            tx.begin();

	            Lead lead = em.find(Lead.class, leadId);
	            SalesEmployee employee = em.find(SalesEmployee.class, empId);

	            if (lead != null && employee != null) {
	                lead.setSalesEmployee(employee);
	                em.merge(lead);
	                System.out.println("Lead Assigned Successfully");
	            } else {
	                System.out.println("Lead or Employee Not Found");
	            }

	            tx.commit();

	        } catch (Exception e) {
	            if (tx.isActive()) tx.rollback();
	        } finally {
	            em.close();
	        }
	    }
	        public void convertLeadToCustomer(Long leadId) {

	            EntityManager em = emf.createEntityManager();
	            EntityTransaction tx = em.getTransaction();

	            try {
	                tx.begin();

	                Lead lead = em.find(Lead.class, leadId);

	                if (lead != null) {

	                    Customer customer = new Customer();
	                    customer.setName("Converted-" + lead.getSource());
	                    customer.setEmail("converted@gmail.com");
	                    customer.setPhone("0000000000");

	                    em.persist(customer);

	                    lead.setStatus("Converted");
	                    em.merge(lead);

	                    System.out.println("Lead Converted Successfully");
	                }

	                tx.commit();

	            } catch (Exception e) {
	                if (tx.isActive()) tx.rollback();
	            } finally {
	                em.close();
	            }
	        
	        
	    }
}