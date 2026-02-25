package pom.capgemini.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import pom.capgemini.entity.Order;
import pom.capgemini.entity.SupportTicket;

public class TicketService {

    private EntityManager entityManager;

    public TicketService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Raise a support ticket for an order
     */
    public Long raiseTicket(Long orderId, String issueDescription) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Order order = entityManager.find(Order.class, orderId);
            if (order == null) {
                System.out.println("✗ Order not found with ID: " + orderId);
                transaction.rollback();
                return null;
            }

            SupportTicket ticket = new SupportTicket(issueDescription, order);
            entityManager.persist(ticket);
            transaction.commit();
            System.out.println("✓ Support ticket raised successfully | Ticket ID: " + ticket.getId() +
                    " | Issue: " + issueDescription);
            return ticket.getId();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error raising ticket: " + e.getMessage());
            return null;
        }
    }

    /**
     * Get ticket by ID
     */
    public SupportTicket getTicket(Long ticketId) {
        try {
            return entityManager.find(SupportTicket.class, ticketId);
        } catch (Exception e) {
            System.out.println("✗ Error retrieving ticket: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update ticket status
     */
    public boolean updateTicketStatus(Long ticketId, String status) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            SupportTicket ticket = entityManager.find(SupportTicket.class, ticketId);
            if (ticket == null) {
                System.out.println("✗ Ticket not found with ID: " + ticketId);
                transaction.rollback();
                return false;
            }
            ticket.setStatus(status);
            entityManager.merge(ticket);
            transaction.commit();
            System.out.println("✓ Ticket status updated successfully to: " + status);
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error updating ticket: " + e.getMessage());
            return false;
        }
    }

    /**
     * Close ticket
     */
    public boolean closeTicket(Long ticketId) {
        return updateTicketStatus(ticketId, "CLOSED");
    }

    /**
     * Delete ticket
     */
    public boolean deleteTicket(Long ticketId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            SupportTicket ticket = entityManager.find(SupportTicket.class, ticketId);
            if (ticket == null) {
                System.out.println("✗ Ticket not found with ID: " + ticketId);
                transaction.rollback();
                return false;
            }
            entityManager.remove(ticket);
            transaction.commit();
            System.out.println("✓ Ticket deleted successfully");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("✗ Error deleting ticket: " + e.getMessage());
            return false;
        }
    }
}

