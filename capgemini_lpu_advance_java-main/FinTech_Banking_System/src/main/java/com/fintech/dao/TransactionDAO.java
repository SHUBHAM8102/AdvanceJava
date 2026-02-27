package com.fintech.dao;

import com.fintech.entity.BankAccount;
import com.fintech.entity.Transaction;
import com.fintech.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class TransactionDAO {

    
    public void save(Transaction transaction, Long accountId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            BankAccount account = em.find(BankAccount.class, accountId);
            if (account != null) {
                transaction.setAccount(account);
                em.persist(transaction);
                em.getTransaction().commit();
                System.out.println("Transaction saved successfully with ID: " + transaction.getTransactionId());
            } else {
                System.out.println("Bank account not found with ID: " + accountId);
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error saving transaction: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    
    public List<Transaction> findByAccount(Long accountId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Transaction> query = em.createQuery(
                "SELECT t FROM Transaction t WHERE t.account.accountId = :accountId ORDER BY t.txnDate DESC",
                Transaction.class);
            query.setParameter("accountId", accountId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public List<Transaction> findByType(String txnType) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Transaction> query = em.createQuery(
                "SELECT t FROM Transaction t WHERE t.txnType = :txnType ORDER BY t.txnDate DESC",
                Transaction.class);
            query.setParameter("txnType", txnType);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public Transaction findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Transaction.class, id);
        } finally {
            em.close();
        }
    }

    
    public List<Transaction> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Transaction> query = em.createQuery(
                "SELECT t FROM Transaction t ORDER BY t.txnDate DESC", Transaction.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Transaction transaction = em.find(Transaction.class, id);
            if (transaction != null) {
                em.remove(transaction);
                em.getTransaction().commit();
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Transaction not found with ID: " + id);
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error deleting transaction: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
}

