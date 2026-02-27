package com.fintech.dao;

import com.fintech.entity.BankAccount;
import com.fintech.entity.Card;
import com.fintech.entity.Customer;
import com.fintech.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class CardDAO {

    
    public void issueCard(Card card) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(card);
            em.getTransaction().commit();
            System.out.println("Card issued successfully with ID: " + card.getCardId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error issuing card: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public void assignCardToCustomer(Long cardId, Long customerId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Card card = em.find(Card.class, cardId);
            Customer customer = em.find(Customer.class, customerId);

            if (card != null && customer != null) {
                customer.addCard(card);
                em.merge(customer);
                em.getTransaction().commit();
                System.out.println("Card assigned to customer successfully.");
            } else {
                System.out.println("Card or customer not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error assigning card to customer: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public void linkCardToAccount(Long cardId, Long accountId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Card card = em.find(Card.class, cardId);
            BankAccount account = em.find(BankAccount.class, accountId);

            if (card != null && account != null) {
                card.setLinkedAccount(account);
                em.merge(card);
                em.getTransaction().commit();
                System.out.println("Card linked to bank account successfully.");
            } else {
                System.out.println("Card or bank account not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error linking card to account: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public void deactivateCard(Long cardId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Card card = em.find(Card.class, cardId);
            if (card != null) {
                card.setIsActive(false);
                em.merge(card);
                em.getTransaction().commit();
                System.out.println("Card deactivated successfully.");
            } else {
                System.out.println("Card not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error deactivating card: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public Card findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Card.class, id);
        } finally {
            em.close();
        }
    }

    
    public Card findByCardNumber(String cardNumber) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Card> query = em.createQuery(
                "SELECT c FROM Card c WHERE c.cardNumber = :cardNumber", Card.class);
            query.setParameter("cardNumber", cardNumber);
            List<Card> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    
    public List<Card> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Card> query = em.createQuery("SELECT c FROM Card c", Card.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Card card = em.find(Card.class, id);
            if (card != null) {
                em.remove(card);
                em.getTransaction().commit();
                System.out.println("Card deleted successfully.");
            } else {
                System.out.println("Card not found with ID: " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error deleting card: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}

