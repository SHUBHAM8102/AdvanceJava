package com.fintech.test;

import com.fintech.dao.BankAccountDAO;
import com.fintech.dao.CardDAO;
import com.fintech.dao.CustomerDAO;
import com.fintech.dao.TransactionDAO;
import com.fintech.entity.BankAccount;
import com.fintech.entity.Card;
import com.fintech.entity.Customer;
import com.fintech.entity.Transaction;
import com.fintech.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TestDataCreator {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  FinTech Banking System - Data Creator");
        System.out.println("  Testing PostgreSQL Connection");
        System.out.println("==============================================\n");

        try {
            
            CustomerDAO customerDAO = new CustomerDAO();
            BankAccountDAO accountDAO = new BankAccountDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            CardDAO cardDAO = new CardDAO();

            System.out.println("✓ DAO objects created successfully");
            System.out.println("✓ Database connection established\n");

            
            System.out.println("--- Creating Customers ---");

            Customer customer1 = new Customer("Amit Kumar", "amit.kumar@email.com", "9876543210");
            customerDAO.save(customer1);
            System.out.println("✓ Customer 1 created: " + customer1.getFullName());

            Customer customer2 = new Customer("Priya Sharma", "priya.sharma@email.com", "9876543211");
            customerDAO.save(customer2);
            System.out.println("✓ Customer 2 created: " + customer2.getFullName());

            Customer customer3 = new Customer("Rahul Verma", "rahul.verma@email.com", "9876543212");
            customerDAO.save(customer3);
            System.out.println("✓ Customer 3 created: " + customer3.getFullName());

            
            System.out.println("\n--- Creating Bank Accounts ---");

            BankAccount account1 = new BankAccount("ACC100001", "SAVINGS", new BigDecimal("50000.00"));
            accountDAO.save(account1);
            System.out.println("✓ Account 1 created: " + account1.getAccountNumber());

            BankAccount account2 = new BankAccount("ACC100002", "CURRENT", new BigDecimal("100000.00"));
            accountDAO.save(account2);
            System.out.println("✓ Account 2 created: " + account2.getAccountNumber());

            BankAccount account3 = new BankAccount("ACC100003", "SALARY", new BigDecimal("75000.00"));
            accountDAO.save(account3);
            System.out.println("✓ Account 3 created: " + account3.getAccountNumber());

            
            System.out.println("\n--- Linking Accounts to Customers ---");

            accountDAO.linkToCustomer(account1.getAccountId(), customer1.getCustomerId());
            System.out.println("✓ Account linked to " + customer1.getFullName());

            accountDAO.linkToCustomer(account2.getAccountId(), customer2.getCustomerId());
            System.out.println("✓ Account linked to " + customer2.getFullName());

            accountDAO.linkToCustomer(account3.getAccountId(), customer3.getCustomerId());
            System.out.println("✓ Account linked to " + customer3.getFullName());

            
            System.out.println("\n--- Creating Transactions ---");

            Transaction txn1 = new Transaction("DEPOSIT", new BigDecimal("10000.00"),
                                              "Salary Credit", "TXN2024001");
            transactionDAO.save(txn1, account1.getAccountId());
            System.out.println("✓ Transaction 1: DEPOSIT - ₹10,000");

            Transaction txn2 = new Transaction("WITHDRAWAL", new BigDecimal("5000.00"),
                                              "ATM Withdrawal", "TXN2024002");
            transactionDAO.save(txn2, account1.getAccountId());
            System.out.println("✓ Transaction 2: WITHDRAWAL - ₹5,000");

            Transaction txn3 = new Transaction("DEPOSIT", new BigDecimal("25000.00"),
                                              "Business Income", "TXN2024003");
            transactionDAO.save(txn3, account2.getAccountId());
            System.out.println("✓ Transaction 3: DEPOSIT - ₹25,000");

            Transaction txn4 = new Transaction("TRANSFER", new BigDecimal("15000.00"),
                                              "Transfer to Vendor", "TXN2024004");
            transactionDAO.save(txn4, account2.getAccountId());
            System.out.println("✓ Transaction 4: TRANSFER - ₹15,000");

            Transaction txn5 = new Transaction("DEPOSIT", new BigDecimal("50000.00"),
                                              "Monthly Salary", "TXN2024005");
            transactionDAO.save(txn5, account3.getAccountId());
            System.out.println("✓ Transaction 5: DEPOSIT - ₹50,000");

            
            System.out.println("\n--- Issuing Cards ---");

            Card card1 = new Card("4532123456789012", "DEBIT", "VISA",
                                 LocalDate.of(2028, 12, 31), true);
            cardDAO.issueCard(card1);
            System.out.println("✓ Card 1 issued: VISA DEBIT");

            Card card2 = new Card("5412345678901234", "CREDIT", "MASTERCARD",
                                 LocalDate.of(2027, 6, 30), true);
            cardDAO.issueCard(card2);
            System.out.println("✓ Card 2 issued: MASTERCARD CREDIT");

            Card card3 = new Card("6062123456789012", "DEBIT", "RUPAY",
                                 LocalDate.of(2029, 3, 31), true);
            cardDAO.issueCard(card3);
            System.out.println("✓ Card 3 issued: RUPAY DEBIT");

            Card card4 = new Card("4532987654321098", "CREDIT", "VISA",
                                 LocalDate.of(2028, 9, 30), true);
            cardDAO.issueCard(card4);
            System.out.println("✓ Card 4 issued: VISA CREDIT");

            
            System.out.println("\n--- Linking Cards to Accounts ---");

            cardDAO.linkCardToAccount(card1.getCardId(), account1.getAccountId());
            System.out.println("  Card 1 linked to Account 1");

            cardDAO.linkCardToAccount(card2.getCardId(), account2.getAccountId());
            System.out.println("  Card 2 linked to Account 2");

            cardDAO.linkCardToAccount(card3.getCardId(), account3.getAccountId());
            System.out.println("  Card 3 linked to Account 3");

            cardDAO.linkCardToAccount(card4.getCardId(), account1.getAccountId());
            System.out.println("  Card 4 linked to Account 1");

            
            System.out.println("\n--- Assigning Cards to Customers ---");

            cardDAO.assignCardToCustomer(card1.getCardId(), customer1.getCustomerId());
            System.out.println("  Card 1 assigned to " + customer1.getFullName());

            cardDAO.assignCardToCustomer(card2.getCardId(), customer2.getCustomerId());
            System.out.println("  Card 2 assigned to " + customer2.getFullName());

            cardDAO.assignCardToCustomer(card3.getCardId(), customer3.getCustomerId());
            System.out.println("  Card 3 assigned to " + customer3.getFullName());

            cardDAO.assignCardToCustomer(card4.getCardId(), customer1.getCustomerId());
            System.out.println("  Card 4 assigned to " + customer1.getFullName());

            
            cardDAO.assignCardToCustomer(card2.getCardId(), customer3.getCustomerId());
            System.out.println(" Card 2 also assigned to " + customer3.getFullName() + " (Joint Card)");

            
            System.out.println("\n==============================================");
            System.out.println("  DATA CREATION COMPLETED SUCCESSFULLY");
            System.out.println("==============================================");
            System.out.println("\nSummary:");
            System.out.println("- 3 Customers created");
            System.out.println("- 3 Bank Accounts created");
            System.out.println("- 5 Transactions recorded");
            System.out.println("- 4 Cards issued");
            System.out.println("- All relationships established");
            System.out.println("\n✓ PostgreSQL database 'fintechdb' is populated with sample data");
            System.out.println("\nYou can now:");
            System.out.println("1. Run FintechApp.java to use the console application");
            System.out.println("2. Query the database using pgAdmin or psql");
            System.out.println("3. View the data using menu option in FintechApp\n");

        } catch (Exception e) {
            System.err.println("\n Error creating test data:");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();

            System.err.println("\n Troubleshooting:");
            System.err.println("1. Ensure PostgreSQL is running");
            System.err.println("2. Check if 'fintechdb' database exists");
            System.err.println("3. Verify username is 'postgres' and password is 'root'");
            System.err.println("4. Check PostgreSQL is running on port 5432");
        } finally {
            
            JPAUtil.close();
            System.out.println("\nDatabase connection closed.");
        }
    }
}

