package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Address;
import pom.capgemini.entity.Customer;

public class CustomerService {

    private EntityManager entityManager;

    public CustomerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Register a new customer
     */
    public Long registerCustomer(String name, String email, String phone) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = new Customer(name, email, phone);
            entityManager.persist(customer);
            transaction.commit();
            System.out.println("✓ Customer registered successfully: " + customer.getName());
            return customer.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error registering customer: " + e.getMessage());
            return null;
        }
    }

    /**
     * Add address to a customer
     */
    public boolean addAddressToCustomer(Long customerId, Address address) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            if (customer == null) {
                System.out.println("✗ Customer not found with ID: " + customerId);
                transaction.rollback();
                return false;
            }
            customer.setAddress(address);
            entityManager.merge(customer);
            transaction.commit();
            System.out.println("✓ Address added successfully to customer: " + customer.getName());
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error adding address: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get customer by ID
     */
    public Customer getCustomer(Long customerId) {
        try {
            return entityManager.find(Customer.class, customerId);
        } catch (Exception e) {
            System.out.println("✗ Error retrieving customer: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update customer
     */
    public boolean updateCustomer(Customer customer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(customer);
            transaction.commit();
            System.out.println("✓ Customer updated successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error updating customer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete customer
     */
    public boolean deleteCustomer(Long customerId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Customer customer = entityManager.find(Customer.class, customerId);
            if (customer == null) {
                System.out.println("✗ Customer not found with ID: " + customerId);
                transaction.rollback();
                return false;
            }
            entityManager.remove(customer);
            transaction.commit();
            System.out.println("✓ Customer deleted successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error deleting customer: " + e.getMessage());
            return false;
        }
    }
}

