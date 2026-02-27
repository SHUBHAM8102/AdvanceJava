package com.fintech.main;

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
import java.util.List;
import java.util.Scanner;

public class FintechApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final BankAccountDAO accountDAO = new BankAccountDAO();
    private static final TransactionDAO transactionDAO = new TransactionDAO();
    private static final CardDAO cardDAO = new CardDAO();

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  Welcome to FinTech Banking System");
        System.out.println("  Powered by Hibernate JPA");
        System.out.println("==============================================\n");

        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addCustomer();
                    break;
                case "2":
                    createBankAccount();
                    break;
                case "3":
                    linkAccountToCustomer();
                    break;
                case "4":
                    recordTransaction();
                    break;
                case "5":
                    issueCard();
                    break;
                case "6":
                    assignCardToCustomer();
                    break;
                case "7":
                    linkCardToAccount();
                    break;
                case "8":
                    viewAllCustomers();
                    break;
                case "9":
                    viewAllAccounts();
                    break;
                case "10":
                    viewAllTransactions();
                    break;
                case "11":
                    viewAllCards();
                    break;
                case "12":
                    viewCustomerDetails();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nThank you for using FinTech Banking System!");
                    System.out.println("Closing application...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        JPAUtil.close();
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n==============================================");
        System.out.println("           MAIN MENU");
        System.out.println("==============================================");
        System.out.println("1.  Add Customer");
        System.out.println("2.  Create Bank Account");
        System.out.println("3.  Link Account to Customer");
        System.out.println("4.  Record Transaction");
        System.out.println("5.  Issue Card");
        System.out.println("6.  Assign Card to Customer");
        System.out.println("7.  Link Card to Account");
        System.out.println("8.  View All Customers");
        System.out.println("9.  View All Bank Accounts");
        System.out.println("10. View All Transactions");
        System.out.println("11. View All Cards");
        System.out.println("12. View Customer Details");
        System.out.println("0.  Exit");
        System.out.println("==============================================");
    }

    private static void addCustomer() {
        System.out.println("\n--- Add New Customer ---");

        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        try {
            Customer customer = new Customer(fullName, email, phone);
            customerDAO.save(customer);
            System.out.println("✓ Customer added successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to add customer: " + e.getMessage());
        }
    }

    private static void createBankAccount() {
        System.out.println("\n--- Create Bank Account ---");

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter account type (SAVINGS/CURRENT/SALARY): ");
        String accountType = scanner.nextLine().toUpperCase();

        System.out.print("Enter initial balance: ");
        String balanceStr = scanner.nextLine();
        BigDecimal balance = new BigDecimal(balanceStr);

        try {
            BankAccount account = new BankAccount(accountNumber, accountType, balance);
            accountDAO.save(account);
            System.out.println("✓ Bank account created successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to create bank account: " + e.getMessage());
        }
    }

    private static void linkAccountToCustomer() {
        System.out.println("\n--- Link Account to Customer ---");

        System.out.print("Enter account ID: ");
        String accountIdStr = scanner.nextLine();
        Long accountId = Long.parseLong(accountIdStr);

        System.out.print("Enter customer ID: ");
        String customerIdStr = scanner.nextLine();
        Long customerId = Long.parseLong(customerIdStr);

        try {
            accountDAO.linkToCustomer(accountId, customerId);
            System.out.println("✓ Account linked to customer successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to link account: " + e.getMessage());
        }
    }

    private static void recordTransaction() {
        System.out.println("\n--- Record Transaction ---");

        System.out.print("Enter account ID: ");
        String accountIdStr = scanner.nextLine();
        Long accountId = Long.parseLong(accountIdStr);

        System.out.print("Enter transaction type (DEPOSIT/WITHDRAWAL/TRANSFER): ");
        String txnType = scanner.nextLine().toUpperCase();

        System.out.print("Enter amount: ");
        String amountStr = scanner.nextLine();
        BigDecimal amount = new BigDecimal(amountStr);

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter reference number: ");
        String referenceNumber = scanner.nextLine();

        try {
            Transaction transaction = new Transaction(txnType, amount, description, referenceNumber);
            transactionDAO.save(transaction, accountId);
            System.out.println("✓ Transaction recorded successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to record transaction: " + e.getMessage());
        }
    }

    private static void issueCard() {
        System.out.println("\n--- Issue Card ---");

        System.out.print("Enter card number (16 digits): ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card type (DEBIT/CREDIT): ");
        String cardType = scanner.nextLine().toUpperCase();

        System.out.print("Enter card network (VISA/MASTERCARD/RUPAY): ");
        String cardNetwork = scanner.nextLine().toUpperCase();

        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        String expiryDateStr = scanner.nextLine();
        LocalDate expiryDate = LocalDate.parse(expiryDateStr);

        try {
            Card card = new Card(cardNumber, cardType, cardNetwork, expiryDate, true);
            cardDAO.issueCard(card);
            System.out.println("✓ Card issued successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to issue card: " + e.getMessage());
        }
    }

    private static void assignCardToCustomer() {
        System.out.println("\n--- Assign Card to Customer ---");

        System.out.print("Enter card ID: ");
        String cardIdStr = scanner.nextLine();
        Long cardId = Long.parseLong(cardIdStr);

        System.out.print("Enter customer ID: ");
        String customerIdStr = scanner.nextLine();
        Long customerId = Long.parseLong(customerIdStr);

        try {
            cardDAO.assignCardToCustomer(cardId, customerId);
            System.out.println("✓ Card assigned to customer successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to assign card: " + e.getMessage());
        }
    }

    private static void linkCardToAccount() {
        System.out.println("\n--- Link Card to Account ---");

        System.out.print("Enter card ID: ");
        String cardIdStr = scanner.nextLine();
        Long cardId = Long.parseLong(cardIdStr);

        System.out.print("Enter account ID: ");
        String accountIdStr = scanner.nextLine();
        Long accountId = Long.parseLong(accountIdStr);

        try {
            cardDAO.linkCardToAccount(cardId, accountId);
            System.out.println("✓ Card linked to account successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to link card: " + e.getMessage());
        }
    }

    private static void viewAllCustomers() {
        System.out.println("\n--- All Customers ---");
        try {
            List<Customer> customers = customerDAO.findAll();
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                System.out.println(String.format("%-5s %-25s %-30s %-15s", "ID", "Name", "Email", "Phone"));
                System.out.println("--------------------------------------------------------------------------------");
                for (Customer customer : customers) {
                    System.out.println(String.format("%-5d %-25s %-30s %-15s",
                            customer.getCustomerId(),
                            customer.getFullName(),
                            customer.getEmail(),
                            customer.getPhone()));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error fetching customers: " + e.getMessage());
        }
    }

    private static void viewAllAccounts() {
        System.out.println("\n--- All Bank Accounts ---");
        try {
            List<BankAccount> accounts = accountDAO.findAll();
            if (accounts.isEmpty()) {
                System.out.println("No bank accounts found.");
            } else {
                System.out.println(String.format("%-5s %-20s %-15s %-15s", "ID", "Account Number", "Type", "Balance"));
                System.out.println("--------------------------------------------------------------------------------");
                for (BankAccount account : accounts) {
                    System.out.println(String.format("%-5d %-20s %-15s %-15s",
                            account.getAccountId(),
                            account.getAccountNumber(),
                            account.getAccountType(),
                            account.getBalance()));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error fetching accounts: " + e.getMessage());
        }
    }

    private static void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        try {
            List<Transaction> transactions = transactionDAO.findAll();
            if (transactions.isEmpty()) {
                System.out.println("No transactions found.");
            } else {
                System.out.println(String.format("%-5s %-15s %-15s %-25s %-20s", "ID", "Type", "Amount", "Date", "Ref Number"));
                System.out.println("----------------------------------------------------------------------------------------");
                for (Transaction txn : transactions) {
                    System.out.println(String.format("%-5d %-15s %-15s %-25s %-20s",
                            txn.getTransactionId(),
                            txn.getTxnType(),
                            txn.getAmount(),
                            txn.getTxnDate(),
                            txn.getReferenceNumber()));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error fetching transactions: " + e.getMessage());
        }
    }

    private static void viewAllCards() {
        System.out.println("\n--- All Cards ---");
        try {
            List<Card> cards = cardDAO.findAll();
            if (cards.isEmpty()) {
                System.out.println("No cards found.");
            } else {
                System.out.println(String.format("%-5s %-20s %-10s %-15s %-15s %-10s", "ID", "Card Number", "Type", "Network", "Expiry", "Active"));
                System.out.println("----------------------------------------------------------------------------------------");
                for (Card card : cards) {
                    System.out.println(String.format("%-5d %-20s %-10s %-15s %-15s %-10s",
                            card.getCardId(),
                            card.getCardNumber(),
                            card.getCardType(),
                            card.getCardNetwork(),
                            card.getExpiryDate(),
                            card.getIsActive()));
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Error fetching cards: " + e.getMessage());
        }
    }

    private static void viewCustomerDetails() {
        System.out.println("\n--- View Customer Details ---");
        System.out.print("Enter customer ID: ");
        String customerIdStr = scanner.nextLine();
        Long customerId = Long.parseLong(customerIdStr);

        try {
            Customer customer = customerDAO.findById(customerId);
            if (customer != null) {
                System.out.println("\n--- Customer Information ---");
                System.out.println("ID: " + customer.getCustomerId());
                System.out.println("Name: " + customer.getFullName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());

                if (customer.getBankAccount() != null) {
                    System.out.println("\n--- Bank Account ---");
                    BankAccount account = customer.getBankAccount();
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Account Type: " + account.getAccountType());
                    System.out.println("Balance: " + account.getBalance());
                }

                if (!customer.getCards().isEmpty()) {
                    System.out.println("\n--- Cards ---");
                    for (Card card : customer.getCards()) {
                        System.out.println("Card Number: " + card.getCardNumber() +
                                         " | Type: " + card.getCardType() +
                                         " | Active: " + card.getIsActive());
                    }
                }
            } else {
                System.out.println("Customer not found with ID: " + customerId);
            }
        } catch (Exception e) {
            System.out.println(" Error fetching customer details: " + e.getMessage());
        }
    }
}
