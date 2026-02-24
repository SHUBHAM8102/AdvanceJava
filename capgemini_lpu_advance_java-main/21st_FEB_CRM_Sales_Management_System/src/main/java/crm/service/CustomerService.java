package crm.service;

import com.Crm.Address;
import com.Crm.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CustomerService {

    // This method is used to register customer
    
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

    public void registerCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
        	et.begin();
            em.persist(customer);
            et.commit();
            System.out.println("Customer Registered Successfully");
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // this method is used to update details of customer
    public void updateCustomer(Customer customer) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
        	et.begin();
            em.merge(customer);
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
        } finally {
            em.close();
        }
    }
    
    // this method will be used for deleting customer
    public void deleteCustomer(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
        	et.begin();
            Customer customer = em.find(Customer.class, id);
            if (customer != null) {
                em.remove(customer);
            }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
        } finally {
            em.close();
        }
        
    }
    public void addAddressToCustomer(Long customerId, Address address) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Customer customer = em.find(Customer.class, customerId);
            if (customer != null) {

                em.persist(address);   // save address first
                customer.setAddress(address);
                em.merge(customer);

                System.out.println("Address Added Successfully");
            } else {
                System.out.println("Customer Not Found");
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


}
