package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Customer;
import pom.capgemini.entity.Lead;
import pom.capgemini.entity.SalesEmployee;

public class LeadService {

    private EntityManager entityManager;

    public LeadService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Create a new lead
     */
    public Long createLead(String name, String source, String contactInfo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Lead lead = new Lead(name, source, contactInfo);
            entityManager.persist(lead);
            transaction.commit();
            System.out.println("✓ Lead created successfully: " + lead.getName());
            return lead.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error creating lead: " + e.getMessage());
            return null;
        }
    }

    /**
     * Assign lead to a sales employee
     */
    public boolean assignLeadToEmployee(Long leadId, Long employeeId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Lead lead = entityManager.find(Lead.class, leadId);
            if (lead == null) {
                System.out.println("✗ Lead not found with ID: " + leadId);
                transaction.rollback();
                return false;
            }
            SalesEmployee employee = entityManager.find(SalesEmployee.class, employeeId);
            if (employee == null) {
                System.out.println("✗ Employee not found with ID: " + employeeId);
                transaction.rollback();
                return false;
            }
            lead.setEmployee(employee);
            entityManager.merge(lead);
            transaction.commit();
            System.out.println("✓ Lead assigned successfully to employee: " + employee.getName());
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error assigning lead: " + e.getMessage());
            return false;
        }
    }

    /**
     * Convert lead to customer
     */
    public boolean convertLeadToCustomer(Long leadId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Lead lead = entityManager.find(Lead.class, leadId);
            if (lead == null) {
                System.out.println("✗ Lead not found with ID: " + leadId);
                transaction.rollback();
                return false;
            }
            if (lead.isConverted()) {
                System.out.println("✗ Lead already converted");
                transaction.rollback();
                return false;
            }

            // Create a new customer from the lead
            Customer customer = new Customer(lead.getName(),
                    lead.getName().toLowerCase().replace(" ", ".") + "@customer.com",
                    lead.getContactInfo());
            entityManager.persist(customer);

            // Mark lead as converted
            lead.setConverted(true);
            entityManager.merge(lead);
            transaction.commit();
            System.out.println("✓ Lead converted to customer successfully: " + customer.getName());
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error converting lead: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get lead by ID
     */
    public Lead getLead(Long leadId) {
        try {
            return entityManager.find(Lead.class, leadId);
        } catch (Exception e) {
            System.out.println("✗ Error retrieving lead: " + e.getMessage());
            return null;
        }
    }
}

