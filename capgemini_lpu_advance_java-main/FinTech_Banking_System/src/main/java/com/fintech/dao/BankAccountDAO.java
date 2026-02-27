package com.fintech.dao;

import com.fintech.entity.BankAccount;
import com.fintech.entity.Customer;
import com.fintech.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;


public class BankAccountDAO {

    
    public void save(BankAccount account) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
            System.out.println("Bank account saved successfully with ID: " + account.getAccountId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error saving bank account: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public BankAccount findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(BankAccount.class, id);
        } finally {
            em.close();
        }
    }

    
    public BankAccount findByAccountNumber(String accountNumber) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BankAccount> query = em.createQuery(
                "SELECT ba FROM BankAccount ba WHERE ba.accountNumber = :accountNumber", BankAccount.class);
            query.setParameter("accountNumber", accountNumber);
            List<BankAccount> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    
    public void linkToCustomer(Long accountId, Long customerId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            BankAccount account = em.find(BankAccount.class, accountId);
            Customer customer = em.find(Customer.class, customerId);

            if (account != null && customer != null) {
                account.setCustomer(customer);
                em.merge(account);
                em.getTransaction().commit();
                System.out.println("Bank account linked to customer successfully.");
            } else {
                System.out.println("Bank account or customer not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error linking bank account to customer: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public void updateBalance(Long accountId, BigDecimal newBalance) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            BankAccount account = em.find(BankAccount.class, accountId);
            if (account != null) {
                account.setBalance(newBalance);
                em.merge(account);
                em.getTransaction().commit();
                System.out.println("Balance updated successfully.");
            } else {
                System.out.println("Bank account not found.");
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error updating balance: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public List<BankAccount> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BankAccount> query = em.createQuery("SELECT ba FROM BankAccount ba", BankAccount.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            BankAccount account = em.find(BankAccount.class, id);
            if (account != null) {
                em.remove(account);
                em.getTransaction().commit();
                System.out.println("Bank account deleted successfully.");
            } else {
                System.out.println("Bank account not found with ID: " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error deleting bank account: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}

