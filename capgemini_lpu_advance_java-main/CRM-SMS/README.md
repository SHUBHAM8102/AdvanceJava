# CRM Sales Management System

A comprehensive Customer Relationship Management (CRM) system built with **Pure Hibernate JPA** and **PostgreSQL**. This console-based application provides complete sales management functionality including customer management, lead tracking, order processing, and support ticket handling.

---

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Configuration](#database-configuration)
- [How to Run](#how-to-run)
- [Application Features](#application-features)
- [Entity Design](#entity-design)
- [Service Layer](#service-layer)
- [Usage Guide](#usage-guide)
- [Database Schema](#database-schema)
- [Troubleshooting](#troubleshooting)

---

## ✨ Features

- ✅ **Customer Management** - Register customers with email, phone, and address
- ✅ **Lead Management** - Create, assign, and convert leads to customers
- ✅ **Product Catalog** - Manage product inventory with pricing
- ✅ **Order Processing** - Place orders with automatic total calculation
- ✅ **Support Tickets** - Track customer issues and support requests
- ✅ **Employee Performance** - Generate detailed performance reports with JPQL
- ✅ **Transaction Management** - Complete ACID compliance with rollback support
- ✅ **Automatic Schema Generation** - Hibernate creates tables automatically
- ✅ **Console-based Interface** - Interactive menu-driven application

---

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Core Programming Language |
| **Hibernate ORM** | 6.4.0.Final | JPA Implementation |
| **Jakarta Persistence API** | 3.1.0 | JPA Specification |
| **PostgreSQL** | 18 | Relational Database |
| **PostgreSQL JDBC Driver** | 42.7.1 | Database Connectivity |
| **Maven** | - | Dependency Management |

---

## 📁 Project Structure

```
CRM-SMS/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
│
├── src/
│   └── main/
│       ├── java/pom/capgemini/
│       │   │
│       │   ├── CRMApplication.java            # Main application entry point
│       │   │
│       │   ├── entity/                        # Entity Classes (7 entities)
│       │   │   ├── Customer.java              # Customer entity with orders
│       │   │   ├── Address.java               # Customer address (OneToOne)
│       │   │   ├── Lead.java                  # Sales lead entity
│       │   │   ├── SalesEmployee.java         # Employee with leads
│       │   │   ├── Product.java               # Product catalog
│       │   │   ├── Order.java                 # Customer orders (ManyToMany with Products)
│       │   │   └── SupportTicket.java         # Support ticket for orders
│       │   │
│       │   ├── service/                       # Service Layer (6 services)
│       │   │   ├── CustomerService.java       # Customer business logic
│       │   │   ├── LeadService.java           # Lead management & conversion
│       │   │   ├── ProductService.java        # Product CRUD operations
│       │   │   ├── OrderService.java          # Order processing
│       │   │   ├── TicketService.java         # Support ticket handling
│       │   │   └── ReportService.java         # Analytics & reporting (JPQL)
│       │   │
│       │   └── util/
│       │       └── EntityManagerUtil.java     # EntityManager utilities
│       │
│       └── resources/
│           └── META-INF/
│               └── persistence.xml            # JPA/Hibernate configuration
│
└── target/                                    # Compiled classes (auto-generated)
```

---

## 📦 Prerequisites

Before running the application, ensure you have:

1. **Java Development Kit (JDK) 21** or higher
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **PostgreSQL Database Server**
   - Download from: https://www.postgresql.org/download/
   - Default port: `5432`
   - Required credentials:
     - Username: `postgres`
     - Password: `root`

3. **Maven** (for dependency management)
   - Usually bundled with IDEs like IntelliJ IDEA
   - Or download from: https://maven.apache.org/download.cgi

4. **IDE** (Recommended)
   - IntelliJ IDEA (Community or Ultimate)
   - Eclipse IDE
   - VS Code with Java extensions

---

## 🚀 Installation & Setup

### Step 1: Clone or Download the Project

```bash
# If using Git
git clone <repository-url>
cd CRM-SMS

# Or download and extract the ZIP file
```

### Step 2: Create PostgreSQL Database

Open PostgreSQL command line (psql) or pgAdmin and execute:

```sql
-- Create database
CREATE DATABASE crm_db;

-- Verify database creation
\l
```

**Alternative**: Use the provided psql command:

```bash
# Windows PowerShell
$env:PGPASSWORD="root"
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "CREATE DATABASE crm_db;"
```

### Step 3: Open Project in IDE

1. Open **IntelliJ IDEA**
2. Click **File → Open**
3. Navigate to `CRM-SMS` folder
4. Click **OK**
5. Wait for Maven to download dependencies (see bottom status bar)

### Step 4: Build the Project

In IntelliJ IDEA:
- Go to **Build → Rebuild Project**
- Wait for compilation to complete
- Check for any errors in the **Build** tab

---

## 🗄️ Database Configuration

The database configuration is in `src/main/resources/META-INF/persistence.xml`:

```xml
<properties>
    <!-- Database Connection -->
    <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>
    <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/crm_db"/>
    <property name="jakarta.persistence.jdbc.user" value="postgres"/>
    <property name="jakarta.persistence.jdbc.password" value="root"/>
    
    <!-- Hibernate Settings -->
    <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
    <property name="hibernate.hbm2ddl.auto" value="update"/>
    <property name="hibernate.show_sql" value="true"/>
    <property name="hibernate.format_sql" value="true"/>
</properties>
```

**Configuration Options:**

| Property | Value | Description |
|----------|-------|-------------|
| `jdbc.url` | `jdbc:postgresql://localhost:5432/crm_db` | Database connection URL |
| `jdbc.user` | `postgres` | Database username |
| `jdbc.password` | `root` | Database password |
| `hibernate.hbm2ddl.auto` | `update` | Auto-create/update tables |
| `hibernate.show_sql` | `true` | Print SQL queries to console |
| `hibernate.format_sql` | `true` | Format SQL for readability |

**To change database credentials:**
1. Open `persistence.xml`
2. Modify the `jdbc.user` and `jdbc.password` properties
3. Rebuild the project

---

## ▶️ How to Run

### Method 1: Run from IntelliJ IDEA (Recommended)

1. Navigate to: `src/main/java/pom/capgemini/CRMApplication.java`
2. Right-click on the file
3. Select **Run 'CRMApplication.main()'**
4. The console menu will appear

### Method 2: Run from Terminal

```bash
# Navigate to project directory
cd CRM-SMS

# Compile (if not already compiled)
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="pom.capgemini.CRMApplication"
```

### Method 3: Run Compiled JAR

```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/CRM-SMS-1.0-SNAPSHOT.jar
```

---

## 🎯 Application Features

When you run the application, you'll see this interactive menu:

```
============================================================
              🏢 CRM SALES MANAGEMENT SYSTEM
============================================================
1.  Register Customer
2.  Add Address to Customer
3.  Create Lead
4.  Assign Lead to Employee
5.  Convert Lead to Customer
6.  Add Product
7.  Place Order
8.  Raise Support Ticket
9.  View Employee Performance
10. View All Employees Performance
11. Create Sample Data (for testing)
12. Exit Application
============================================================
Enter your choice (1-12):
```

### Feature Details

#### 1️⃣ Register Customer
- Creates a new customer in the system
- Input: Name, Email, Phone
- Email must be unique
- Returns: Customer ID

#### 2️⃣ Add Address to Customer
- Adds physical address to existing customer
- Input: Customer ID, Street, City, State, Zip Code
- Relationship: OneToOne with Customer

#### 3️⃣ Create Lead
- Creates a new sales lead
- Input: Name, Source (Website/Email/Phone), Contact Info
- Status: Initially unconverted

#### 4️⃣ Assign Lead to Employee
- Assigns a lead to a sales employee for follow-up
- Input: Lead ID, Employee ID
- Updates lead's assigned employee

#### 5️⃣ Convert Lead to Customer
- Converts a successful lead into a customer
- Input: Lead ID
- Creates new customer from lead data
- Marks lead as converted

#### 6️⃣ Add Product
- Adds a new product to catalog
- Input: Product Name, Price
- Products can be included in orders

#### 7️⃣ Place Order
- Creates an order for a customer
- Input: Customer ID, Product IDs (comma-separated)
- Automatically calculates total amount
- Relationship: ManyToMany with Products

#### 8️⃣ Raise Support Ticket
- Creates a support ticket for an order
- Input: Order ID, Issue Description
- Initial status: "OPEN"

#### 9️⃣ View Employee Performance
- Shows individual employee performance metrics
- Input: Employee ID
- Displays:
  - Total leads assigned
  - Converted leads
  - Unconverted leads
  - Conversion rate percentage

#### 🔟 View All Employees Performance
- Displays performance report for all employees
- Shows comparative metrics
- Useful for management reporting

#### 1️⃣1️⃣ Create Sample Data
- Automatically creates test data:
  - 3 Sample employees
  - 3 Sample customers
  - 3 Sample products
  - 5 Sample leads
  - 2 Sample orders
  - 1 Support ticket
- Useful for testing and demonstration

---

## 🗂️ Entity Design

### 1. Customer Entity

**Fields:**
- `id` (Long) - Primary key, auto-generated
- `name` (String) - Customer name
- `email` (String) - Unique email address
- `phone` (String) - Contact number
- `address` (Address) - OneToOne relationship
- `orders` (List<Order>) - OneToMany relationship

**Relationships:**
- **OneToOne** with `Address` (Cascade ALL)
- **OneToMany** with `Order` (Bidirectional)

**Table:** `customers`

---

### 2. Address Entity

**Fields:**
- `id` (Long) - Primary key
- `street` (String)
- `city` (String)
- `state` (String)
- `zipCode` (String)

**Relationships:**
- **OneToOne** with `Customer` (Owner side)

**Table:** `addresses`

---

### 3. Lead Entity

**Fields:**
- `id` (Long) - Primary key
- `name` (String) - Lead name
- `source` (String) - Lead source (Website, Email, etc.)
- `contactInfo` (String) - Contact information
- `converted` (boolean) - Conversion status (default: false)
- `employee` (SalesEmployee) - ManyToOne relationship

**Relationships:**
- **ManyToOne** with `SalesEmployee`

**Table:** `leads`

---

### 4. SalesEmployee Entity

**Fields:**
- `id` (Long) - Primary key
- `name` (String) - Employee name
- `department` (String) - Department name
- `leads` (List<Lead>) - OneToMany relationship

**Relationships:**
- **OneToMany** with `Lead` (Bidirectional)

**Table:** `sales_employees`

---

### 5. Product Entity

**Fields:**
- `id` (Long) - Primary key
- `name` (String) - Product name
- `price` (double) - Product price

**Relationships:**
- **ManyToMany** with `Order` (via join table)

**Table:** `products`

---

### 6. Order Entity

**Fields:**
- `id` (Long) - Primary key
- `orderDate` (LocalDate) - Date of order
- `totalAmount` (double) - Total order amount
- `customer` (Customer) - ManyToOne relationship
- `products` (List<Product>) - ManyToMany relationship

**Relationships:**
- **ManyToOne** with `Customer`
- **ManyToMany** with `Product`
- **OneToMany** with `SupportTicket`

**Table:** `orders`
**Join Table:** `order_products`

---

### 7. SupportTicket Entity

**Fields:**
- `id` (Long) - Primary key
- `issueDescription` (String) - Description of issue
- `status` (String) - Ticket status (default: "OPEN")
- `order` (Order) - ManyToOne relationship

**Relationships:**
- **ManyToOne** with `Order`

**Table:** `support_tickets`

---

## 🔧 Service Layer

### CustomerService

**Methods:**
- `registerCustomer(String name, String email, String phone)` → Long
- `addAddressToCustomer(Long customerId, Address address)` → boolean
- `getCustomer(Long customerId)` → Customer
- `updateCustomer(Customer customer)` → boolean
- `deleteCustomer(Long customerId)` → boolean

**Features:**
- Customer registration with validation
- Address management
- Transaction handling with rollback

---

### LeadService

**Methods:**
- `createLead(String name, String source, String contactInfo)` → Long
- `assignLeadToEmployee(Long leadId, Long employeeId)` → boolean
- `convertLeadToCustomer(Long leadId)` → boolean
- `getLead(Long leadId)` → Lead

**Features:**
- Lead creation and assignment
- Lead to customer conversion
- Automatic email generation for converted leads

---

### ProductService

**Methods:**
- `addProduct(String name, double price)` → Long
- `getProduct(Long productId)` → Product
- `updateProduct(Product product)` → boolean
- `deleteProduct(Long productId)` → boolean

**Features:**
- Product catalog management
- CRUD operations with validation

---

### OrderService

**Methods:**
- `placeOrder(Long customerId, List<Long> productIds)` → Long
- `getOrder(Long orderId)` → Order
- `calculateOrderTotal(Long orderId)` → double
- `updateOrder(Order order)` → boolean
- `deleteOrder(Long orderId)` → boolean

**Features:**
- Multi-product order processing
- Automatic total calculation
- Product validation

---

### TicketService

**Methods:**
- `raiseTicket(Long orderId, String issueDescription)` → Long
- `getTicket(Long ticketId)` → SupportTicket
- `updateTicketStatus(Long ticketId, String status)` → boolean
- `closeTicket(Long ticketId)` → boolean
- `deleteTicket(Long ticketId)` → boolean

**Features:**
- Support ticket creation
- Status management (OPEN/CLOSED)
- Order association

---

### ReportService

**Methods:**
- `getEmployeePerformance(Long employeeId)` → void
- `getAllEmployeesPerformance()` → void
- `getTopPerformers()` → void

**Features:**
- JPQL-based reporting
- Lead conversion analytics
- Performance metrics calculation
- Top performers ranking

---

## 📊 Database Schema

Hibernate automatically creates the following tables:

### Tables Created

| Table Name | Description | Key Relationships |
|------------|-------------|-------------------|
| `customers` | Customer information | FK to addresses |
| `addresses` | Customer addresses | Referenced by customers |
| `leads` | Sales leads | FK to sales_employees |
| `sales_employees` | Sales team members | Referenced by leads |
| `products` | Product catalog | Referenced by order_products |
| `orders` | Customer orders | FK to customers |
| `order_products` | Order-Product mapping | FK to orders, products |
| `support_tickets` | Support requests | FK to orders |

### Entity Relationships Diagram

```
Customer (1) ←→ (1) Address
Customer (1) ←→ (*) Order
Order (*) ←→ (*) Product [via order_products]
Order (1) ←→ (*) SupportTicket
SalesEmployee (1) ←→ (*) Lead
```

### Constraints

- **Unique Constraints:**
  - `customers.email` - Ensures unique email addresses
  - `customers.address_id` - One address per customer

- **Foreign Keys:**
  - All relationships enforced with foreign key constraints
  - Cascade operations configured (ALL, PERSIST, etc.)

---

## 📖 Usage Guide

### Example 1: Complete Customer Flow

```
Step 1: Register Customer
  → Select option 1
  → Enter: "John Doe", "john@email.com", "123-456-7890"
  → Get Customer ID: 1

Step 2: Add Address
  → Select option 2
  → Enter Customer ID: 1
  → Enter: "123 Main St", "New York", "NY", "10001"

Step 3: Place Order
  → First, add products (option 6)
  → Then select option 7
  → Enter Customer ID: 1
  → Enter Product IDs: 1,2,3
  → Order created with total amount

Step 4: Raise Support Ticket (if needed)
  → Select option 8
  → Enter Order ID
  → Enter issue description
```

### Example 2: Lead Management Flow

```
Step 1: Create Lead
  → Select option 3
  → Enter: "Jane Smith", "Website", "jane@email.com"
  → Get Lead ID: 1

Step 2: Assign to Employee
  → Select option 4
  → Enter Lead ID: 1
  → Enter Employee ID: 1

Step 3: Convert to Customer
  → Select option 5
  → Enter Lead ID: 1
  → Lead converted to customer automatically
```

### Example 3: Quick Testing

```
Step 1: Create Sample Data
  → Select option 11
  → System creates:
    - 3 Employees
    - 3 Customers
    - 3 Products
    - 5 Leads
    - 2 Orders

Step 2: View Reports
  → Select option 9 or 10
  → See employee performance metrics
```

---

## 🔍 Troubleshooting

### Issue 1: Database Connection Failed

**Error:** `Could not connect to database`

**Solution:**
1. Check if PostgreSQL is running:
   ```bash
   # Windows
   services.msc → Look for PostgreSQL service
   
   # Or check with psql
   psql -U postgres -d crm_db
   ```

2. Verify credentials in `persistence.xml`:
   - Username: `postgres`
   - Password: `root`
   - Database: `crm_db`

3. Check if database exists:
   ```sql
   \l  -- List all databases
   ```

---

### Issue 2: No Persistence Provider

**Error:** `No Persistence provider for EntityManager`

**Solution:**
1. Clean and rebuild project:
   ```
   Build → Clean Project
   Build → Rebuild Project
   ```

2. Check Maven dependencies downloaded:
   - Right-click `pom.xml`
   - Maven → Reload Project

3. Verify `persistence.xml` location:
   - Must be in: `src/main/resources/META-INF/`

---

### Issue 3: Port Already in Use

**Error:** `Port 5432 already in use`

**Solution:**
1. Check if another PostgreSQL instance is running
2. Or change port in `persistence.xml`:
   ```xml
   jdbc:postgresql://localhost:PORT/crm_db
   ```

---

### Issue 4: Maven Dependencies Not Downloading

**Solution:**
1. Check internet connection
2. Clear Maven cache:
   ```bash
   # Windows
   del /s /q %USERPROFILE%\.m2\repository
   
   # Then reload project
   ```

3. Update Maven settings for proxy (if behind corporate firewall)

---

### Issue 5: Compilation Errors

**Solution:**
1. Ensure JDK 21 is configured:
   - File → Project Structure → Project SDK
   - Set to JDK 21

2. Invalidate caches:
   - File → Invalidate Caches / Restart

3. Check for missing imports in source files

---

## 📝 Notes

### Important Points

1. **Database Tables:**
   - Tables are created automatically by Hibernate
   - No manual SQL scripts needed
   - Schema updates automatically on entity changes

2. **Transaction Management:**
   - All operations are wrapped in transactions
   - Automatic rollback on errors
   - ACID compliance maintained

3. **Data Persistence:**
   - All data persists in PostgreSQL
   - Survives application restarts
   - Can be queried using pgAdmin or psql

4. **Sample Data:**
   - Use option 11 for quick testing
   - Creates realistic test data
   - Safe to run multiple times (creates new records)

5. **Performance:**
   - JPQL queries optimized for reporting
   - Lazy loading for collections
   - Connection pooling enabled (size: 20)

---

## 🎓 Learning Objectives

This project demonstrates:

✅ **JPA/Hibernate Concepts:**
- Entity mapping with annotations
- Relationship types (OneToOne, OneToMany, ManyToOne, ManyToMany)
- Cascade operations
- Lazy vs Eager loading
- JPQL queries

✅ **Design Patterns:**
- Service Layer pattern
- Data Access Object (DAO) pattern
- Singleton pattern (EntityManagerFactory)

✅ **Best Practices:**
- Transaction management
- Exception handling
- Resource cleanup (try-with-resources)
- Separation of concerns
- Console-based user interaction

---

## 📧 Support

For issues or questions:
1. Check the [Troubleshooting](#troubleshooting) section
2. Review Hibernate logs in console
3. Check PostgreSQL logs
4. Verify all prerequisites are installed

---

## 📄 License

This project is created for educational purposes.

---

## 🎉 Conclusion

Your CRM Sales Management System is now ready to use! This application provides a complete solution for managing customers, leads, orders, and support tickets with the power of Hibernate JPA and PostgreSQL.

**Key Achievements:**
- ✅ Full CRUD operations for all entities
- ✅ Complex relationship mapping
- ✅ Transaction-safe operations
- ✅ Performance reporting with JPQL
- ✅ Automatic schema generation
- ✅ Console-based user interface

**Happy Coding! 🚀**

---

*Last Updated: February 24, 2026*

