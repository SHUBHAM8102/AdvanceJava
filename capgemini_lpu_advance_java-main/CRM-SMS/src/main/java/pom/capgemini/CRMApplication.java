package pom.capgemini;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pom.capgemini.entity.Address;
import pom.capgemini.entity.SalesEmployee;
import pom.capgemini.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CRMApplication {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static CustomerService customerService;
    private static LeadService leadService;
    private static ProductService productService;
    private static OrderService orderService;
    private static TicketService ticketService;
    private static ReportService reportService;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            // Initialize EntityManagerFactory and EntityManager
            System.out.println("🔄 Initializing CRM System...");
            emf = Persistence.createEntityManagerFactory("CRM_PU");
            em = emf.createEntityManager();
            System.out.println("✓ Database connection established!\n");

            // Initialize services
            customerService = new CustomerService(em);
            leadService = new LeadService(em);
            productService = new ProductService(em);
            orderService = new OrderService(em);
            ticketService = new TicketService(em);
            reportService = new ReportService(em);

            // Create scanner for user input
            scanner = new Scanner(System.in);

            // Display main menu in a loop
            boolean running = true;
            while (running) {
                displayMainMenu();
                int choice = getUserChoice();
                running = handleMenuChoice(choice);
            }

        } catch (Exception e) {
            System.out.println("✗ Fatal Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close EntityManager and EntityManagerFactory
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("✓ Application closed successfully");
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏢 CRM SALES MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("1. Register Customer");
        System.out.println("2. Add Address to Customer");
        System.out.println("3. Create Lead");
        System.out.println("4. Assign Lead to Employee");
        System.out.println("5. Convert Lead to Customer");
        System.out.println("6. Add Product");
        System.out.println("7. Place Order");
        System.out.println("8. Raise Support Ticket");
        System.out.println("9. View Employee Performance");
        System.out.println("10. View All Employees Performance");
        System.out.println("11. Create Sample Data (for testing)");
        System.out.println("12. Exit Application");
        System.out.println("=".repeat(60));
        System.out.print("Enter your choice (1-12): ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid input. Please enter a number.");
            return -1;
        }
    }

    private static boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                registerCustomer();
                break;
            case 2:
                addAddressToCustomer();
                break;
            case 3:
                createLead();
                break;
            case 4:
                assignLeadToEmployee();
                break;
            case 5:
                convertLeadToCustomer();
                break;
            case 6:
                addProduct();
                break;
            case 7:
                placeOrder();
                break;
            case 8:
                raiseTicket();
                break;
            case 9:
                viewEmployeePerformance();
                break;
            case 10:
                viewAllEmployeesPerformance();
                break;
            case 11:
                createSampleData();
                break;
            case 12:
                System.out.println("\n✓ Thank you for using CRM System!");
                return false;
            default:
                System.out.println("✗ Invalid choice. Please try again.");
        }
        return true;
    }

    private static void registerCustomer() {
        System.out.print("\nEnter customer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            System.out.println("✗ All fields are required");
            return;
        }

        customerService.registerCustomer(name, email, phone);
    }

    private static void addAddressToCustomer() {
        System.out.print("\nEnter customer ID: ");
        try {
            Long customerId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter street: ");
            String street = scanner.nextLine().trim();
            System.out.print("Enter city: ");
            String city = scanner.nextLine().trim();
            System.out.print("Enter state: ");
            String state = scanner.nextLine().trim();
            System.out.print("Enter zip code: ");
            String zipCode = scanner.nextLine().trim();

            if (street.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
                System.out.println("✗ All fields are required");
                return;
            }

            Address address = new Address(street, city, state, zipCode);
            customerService.addAddressToCustomer(customerId, address);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid customer ID");
        }
    }

    private static void createLead() {
        System.out.print("\nEnter lead name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter lead source (e.g., Website, Email, Phone): ");
        String source = scanner.nextLine().trim();
        System.out.print("Enter contact info: ");
        String contactInfo = scanner.nextLine().trim();

        if (name.isEmpty() || source.isEmpty() || contactInfo.isEmpty()) {
            System.out.println("✗ All fields are required");
            return;
        }

        leadService.createLead(name, source, contactInfo);
    }

    private static void assignLeadToEmployee() {
        System.out.print("\nEnter lead ID: ");
        try {
            Long leadId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter employee ID: ");
            Long employeeId = Long.parseLong(scanner.nextLine());

            leadService.assignLeadToEmployee(leadId, employeeId);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format");
        }
    }

    private static void convertLeadToCustomer() {
        System.out.print("\nEnter lead ID: ");
        try {
            Long leadId = Long.parseLong(scanner.nextLine());
            leadService.convertLeadToCustomer(leadId);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid lead ID");
        }
    }

    private static void addProduct() {
        System.out.print("\nEnter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter product price: ");
        try {
            double price = Double.parseDouble(scanner.nextLine());

            if (name.isEmpty() || price < 0) {
                System.out.println("✗ Invalid product data");
                return;
            }

            productService.addProduct(name, price);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid price format");
        }
    }

    private static void placeOrder() {
        System.out.print("\nEnter customer ID: ");
        try {
            Long customerId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter product IDs (comma-separated): ");
            String productInput = scanner.nextLine().trim();

            List<Long> productIds = new ArrayList<>();
            for (String id : productInput.split(",")) {
                try {
                    productIds.add(Long.parseLong(id.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("✗ Invalid product ID: " + id);
                }
            }

            if (productIds.isEmpty()) {
                System.out.println("✗ No valid product IDs provided");
                return;
            }

            orderService.placeOrder(customerId, productIds);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid customer ID");
        }
    }

    private static void raiseTicket() {
        System.out.print("\nEnter order ID: ");
        try {
            Long orderId = Long.parseLong(scanner.nextLine());
            System.out.print("Enter issue description: ");
            String issueDescription = scanner.nextLine().trim();

            if (issueDescription.isEmpty()) {
                System.out.println("✗ Issue description is required");
                return;
            }

            ticketService.raiseTicket(orderId, issueDescription);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid order ID");
        }
    }

    private static void viewEmployeePerformance() {
        System.out.print("\nEnter employee ID: ");
        try {
            Long employeeId = Long.parseLong(scanner.nextLine());
            reportService.getEmployeePerformance(employeeId);
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid employee ID");
        }
    }

    private static void viewAllEmployeesPerformance() {
        reportService.getAllEmployeesPerformance();
    }

    private static void createSampleData() {
        System.out.println("\n⏳ Creating sample data...");
        try {
            // Create sample employees
            SalesEmployee emp1 = new SalesEmployee("John Smith", "Sales");
            SalesEmployee emp2 = new SalesEmployee("Sarah Johnson", "Sales");
            SalesEmployee emp3 = new SalesEmployee("Mike Davis", "Business Development");

            em.getTransaction().begin();
            em.persist(emp1);
            em.persist(emp2);
            em.persist(emp3);
            em.getTransaction().commit();
            System.out.println("✓ 3 sample employees created (IDs: 1, 2, 3)");

            // Create sample customers
            Long cust1 = customerService.registerCustomer("Alice Brown", "alice.brown@email.com", "555-0001");
            Long cust2 = customerService.registerCustomer("Bob Wilson", "bob.wilson@email.com", "555-0002");
            Long cust3 = customerService.registerCustomer("Carol Martinez", "carol.martinez@email.com", "555-0003");

            // Add addresses
            customerService.addAddressToCustomer(cust1, new Address("123 Main St", "New York", "NY", "10001"));
            customerService.addAddressToCustomer(cust2, new Address("456 Oak Ave", "Los Angeles", "CA", "90001"));

            // Create sample products
            Long prod1 = productService.addProduct("Laptop", 999.99);
            Long prod2 = productService.addProduct("Mouse", 29.99);
            Long prod3 = productService.addProduct("Keyboard", 79.99);

            // Create sample leads
            Long lead1 = leadService.createLead("David Lee", "Website", "555-0010");
            Long lead2 = leadService.createLead("Emma Wilson", "Email", "555-0011");

            // Assign leads to employees
            leadService.assignLeadToEmployee(lead1, 1L);
            leadService.assignLeadToEmployee(lead2, 1L);

            // Convert some leads
            leadService.convertLeadToCustomer(lead1);

            // Place orders
            List<Long> orderProducts = new ArrayList<>();
            orderProducts.add(prod1);
            orderProducts.add(prod2);
            Long order1 = orderService.placeOrder(cust1, orderProducts);

            orderProducts.clear();
            orderProducts.add(prod3);
            Long order2 = orderService.placeOrder(cust2, orderProducts);

            // Create support tickets
            if (order1 != null) {
                ticketService.raiseTicket(order1, "Laptop screen has dead pixels");
            }
            if (order2 != null) {
                ticketService.raiseTicket(order2, "Keyboard keys are not responding");
            }

            System.out.println("\n✓ Sample data created successfully!");
            System.out.println("  - Employees: 1, 2, 3");
            System.out.println("  - Customers: " + cust1 + ", " + cust2 + ", " + cust3);
            System.out.println("  - Products: " + prod1 + ", " + prod2 + ", " + prod3);
            System.out.println("  - Orders: " + order1 + ", " + order2);

        } catch (Exception e) {
            System.out.println("✗ Error creating sample data: " + e.getMessage());
        }
    }
}



