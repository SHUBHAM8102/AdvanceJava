package crm.service;

import java.util.HashSet;
import java.util.Set;

import com.Crm.Lead;
import com.Crm.Order;
import com.Crm.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class OrderService {
	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
//
//	    public void placeOrder(Order order) {
//	        executeTransaction(em -> {
//	            double total = order.getProducts()
//	                                .stream()
//	                                .mapToDouble(Product::getPrice)
//	                                .sum();
//
//	            order.setTotalAmount(total);
//	            em.persist(order);
//	        });
//	    }

	 public void placeOrder(Long leadId, Long productId) {

		    EntityManager em = emf.createEntityManager();
		    EntityTransaction et = em.getTransaction();

		    try {
		        et.begin();

		        Lead lead = em.find(Lead.class, leadId);
		        Product product = em.find(Product.class, productId);

		        if (lead != null && product != null) {

		            Order order = new Order();
		            order.setLead(lead);   // ✅ correct relationship
		            order.setTotalAmount(product.getPrice());

		            Set<Product> products = new HashSet<>();
		            products.add(product);

		            order.setProducts(products);

		            em.persist(order);

		            System.out.println("Order Placed Successfully");
		        }

		        et.commit();

		    } catch (Exception e) {
		        if (et.isActive()) et.rollback();
		    } finally {
		        em.close();
		    }
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

		
		

}
