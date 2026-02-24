package crm.service;

import java.util.List;

import com.Crm.Customer;
import com.Crm.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ReportService {
	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

	    public List<Order> getOrdersAboveAmount(double amount) {
	        EntityManager em = emf.createEntityManager();
	        return em.createQuery(
	                "SELECT o FROM Order o WHERE o.totalAmount > :amt",
	                Order.class)
	                .setParameter("amt", amount)
	                .getResultList();
	    }

	    public List<Customer> getCustomersByCity(String city) {
	        EntityManager em = emf.createEntityManager();
	        return em.createQuery(
	                "SELECT c FROM Customer c WHERE c.address.city = :city",
	                Customer.class)
	                .setParameter("city", city)
	                .getResultList();
	    }
	    public void getEmployeePerformance() {

	        EntityManager em = emf.createEntityManager();

	        List<Object[]> results = em.createQuery(
	                "SELECT l.salesEmployee.name, COUNT(l) " +
	                "FROM Lead l GROUP BY l.salesEmployee.name",
	                Object[].class
	        ).getResultList();

	        for (Object[] row : results) {
	            System.out.println("Employee: " + row[0] + 
	                               " | Leads Handled: " + row[1]);
	        }

	        em.close();
	    }

}
