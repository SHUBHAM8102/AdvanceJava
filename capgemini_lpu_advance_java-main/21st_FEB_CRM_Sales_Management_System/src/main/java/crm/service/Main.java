package crm.service;

import java.util.Scanner;

import com.Crm.Address;
import com.Crm.Customer;
import com.Crm.Lead;
import com.Crm.Product;
import com.Crm.Support_Ticket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	 public static void main(String[] args) {

	        // Create EntityManagerFactory
	        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");

	        // Create EntityManager
	        EntityManager em = emf.createEntityManager();

	        // Scanner for input
	        Scanner sc = new Scanner(System.in);

	        // Create Service Objects
	        CustomerService customerService = new CustomerService();
	        LeadService leadService = new LeadService();
	        ProductService productService = new ProductService();
	        OrderService orderService = new OrderService();
	        TicketService ticketService = new TicketService();
	        ReportService reportService = new ReportService();

	        int choice;

	        // Menu Loop
	        while (true) {

	            System.out.println("\n===== CRM APPLICATION MENU =====");
	            System.out.println("1. Register Customer");
	            System.out.println("2. Add Address To Customer");
	            System.out.println("3. Create Lead");
	            System.out.println("4. Assign Lead To Employee");
	            System.out.println("5. Convert Lead To Customer");
	            System.out.println("6. Add Product");
	            System.out.println("7. Place Order");
	            System.out.println("8. Raise Support Ticket");
	            System.out.println("9. Employee Performance Report");
	            System.out.println("10. Exit");

	            System.out.print("Enter your choice: ");
	            choice = sc.nextInt();
	            sc.nextLine(); // clear buffer

	            switch (choice) {

	                case 1:
	                    // Register Customer
	                    Customer customer = new Customer();
	                    System.out.print("Enter Name: ");
	                    customer.setName(sc.nextLine());
	                    System.out.print("Enter Email: ");
	                    customer.setEmail(sc.nextLine());
	                    System.out.print("Enter Phone: ");
	                    customer.setPhone(sc.nextLine());

	                    customerService.registerCustomer(customer);
	                    break;

	                case 2:
	                    // Add Address
	                    System.out.print("Enter Customer ID: ");
	                    Long custId = sc.nextLong();
	                    sc.nextLine();

	                    Address address = new Address();
	                    System.out.print("Enter City: ");
	                    address.setCity(sc.nextLine());
	                    System.out.print("Enter State: ");
	                    address.setState(sc.nextLine());
	                    System.out.print("Enter Pincode: ");
	                    address.setPincode(sc.nextLine());

	                    customerService.addAddressToCustomer(custId, address);
	                    break;

	                case 3:
	                    // Create Lead
	                    Lead lead = new Lead();
	                    System.out.print("Enter Lead Source: ");
	                    lead.setSource(sc.nextLine());
	                    System.out.print("Enter Lead Status: ");
	                    lead.setStatus(sc.nextLine());

	                    leadService.createLead(lead);
	                    break;

	                case 4:
	                    // Assign Lead
	                    System.out.print("Enter Lead ID: ");
	                    Long leadId = sc.nextLong();
	                    System.out.print("Enter Employee ID: ");
	                    long empId = sc.nextLong();

	                    leadService.assignLeadToEmployee(leadId, empId);
	                    break;

	                case 5:
	                    // Convert Lead
	                    System.out.print("Enter Lead ID to Convert: ");
	                    Long convertLeadId = sc.nextLong();

	                    leadService.convertLeadToCustomer(convertLeadId);
	                    break;

	                case 6:
	                    // Add Product
	                    Product product = new Product();
	                    System.out.print("Enter Product Name: ");
	                    product.setProductName(sc.nextLine());
	                    System.out.print("Enter Price: ");
	                    product.setPrice(sc.nextDouble());

	                    productService.createProduct(product);
	                    break;

	                case 7:
	                    // Place Order
	                    System.out.print("Enter Customer ID: ");
	                    Long orderCustId = sc.nextLong();

	                    System.out.print("Enter Product ID: ");
	                    Long productId = sc.nextLong();

	                    orderService.placeOrder(orderCustId, productId);
	                    break;

	                case 8:
	                    // Raise Ticket
	                    Support_Ticket ticket = new Support_Ticket();
	                    System.out.print("Enter Issue: ");
	                    ticket.setIssue(sc.nextLine());
	                    ticket.setStatus("OPEN");

	                    ticketService.raiseTicket(ticket);
	                    break;

	                case 9:
	                    // Report
	                    reportService.getEmployeePerformance();
	                    break;

	                case 10:
	                    System.out.println("Exiting from Application...");
	                    em.close();
	                    emf.close();
	                    sc.close();
	                    System.exit(0);

	                default:
	                    System.out.println("Invalid Choice!");
	            }
	        }
	    }

}
