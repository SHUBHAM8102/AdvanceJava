# FinTech Banking System (Hibernate JPA)

## Overview
A console-based FinTech Banking System that demonstrates Hibernate JPA with four core entities and all major relationship types. The project uses a DAO layer and PostgreSQL as the database.

## Features
- Four entities: Customer, BankAccount, Transaction, Card
- Relationship types:
  - One-to-One (bidirectional): Customer <-> BankAccount
  - One-to-Many (bidirectional): BankAccount <-> Transaction
  - Many-to-Many (bidirectional): Customer <-> Card
  - Many-to-One (unidirectional): Card -> BankAccount
- DAO layer for CRUD operations
- Console UI for common banking actions
- PostgreSQL database configuration

## Tech Stack
- Java 18
- Hibernate ORM 6.4.4
- Jakarta Persistence API 3.1
- PostgreSQL 18
- Maven

## Project Structure
```
FinTech_Banking_System/
  pom.xml
  README.md
  src/
    main/
      java/
        com/
          fintech/
            dao/
              BankAccountDAO.java
              CardDAO.java
              CustomerDAO.java
              TransactionDAO.java
            entity/
              BankAccount.java
              Card.java
              Customer.java
              Transaction.java
            main/
              FintechApp.java
            util/
              JPAUtil.java
      resources/
        META-INF/
          persistence.xml
```

## Core Concepts (What This Project Demonstrates)

### 1) ORM and JPA
- JPA annotations map Java classes to database tables.
- Hibernate acts as the JPA implementation and generates SQL at runtime.
- Entities are plain Java classes annotated with `@Entity` and related mappings.

### 2) Entity Lifecycle and EntityManager
- `EntityManager` handles CRUD operations and transactions.
- Typical flow: begin -> persist/merge/remove -> commit -> close.
- `JPAUtil` creates a single `EntityManagerFactory` and hands out `EntityManager` instances.

### 3) Relationships

#### One-to-One (Bidirectional)
- Customer <-> BankAccount
- Owner: `BankAccount` (has the foreign key column `customer_id`).
- Inverse: `Customer` uses `mappedBy`.

#### One-to-Many / Many-to-One (Bidirectional)
- BankAccount <-> Transaction
- Owner: `Transaction` (has `account_id`).
- Inverse: `BankAccount` uses `mappedBy`.

#### Many-to-Many (Bidirectional)
- Customer <-> Card
- Owner: `Customer` uses join table `customer_card`.
- Inverse: `Card` uses `mappedBy`.

#### Many-to-One (Unidirectional)
- Card -> BankAccount
- `Card` has `account_id`. BankAccount does not reference cards for this mapping.

### 4) Cascading and Fetching
- Cascades ensure child entities are saved/updated/removed with the parent when needed.
- Fetch type is mostly LAZY to avoid loading large graphs by default.

### 5) Schema Generation
- `hibernate.hbm2ddl.auto=update` creates/updates tables on startup.
- This is suitable for development; switch to `validate` for stricter checks.

## Database Configuration
PostgreSQL is configured in `src/main/resources/META-INF/persistence.xml`.

Default values:
- URL: `jdbc:postgresql://localhost:5432/fintechdb`
- User: `postgres`
- Password: `root`

You can change these values if your setup differs.

## Setup Instructions

### 1) Create the database
If `psql` is in PATH:
```sql
CREATE DATABASE fintechdb;
```

If `psql` is not in PATH (PowerShell):
```powershell
$env:PGPASSWORD = "root"
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "CREATE DATABASE fintechdb;"
```

### 2) Build the project
If `mvn` is in PATH:
```bash
mvn clean install
```

If `mvn` is not in PATH (IntelliJ bundled Maven):
```powershell
& "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3\plugins\maven\lib\maven3\bin\mvn.cmd" clean install
```

### 3) Run the application
```bash
mvn exec:java -Dexec.mainClass="com.fintech.main.FintechApp"
```

### Optional: Insert sample data
This project includes a sample data loader:
```bash
mvn exec:java -Dexec.mainClass="com.fintech.test.TestDataCreator"
```

## Console Menu
The application provides these options:
1. Add Customer
2. Create Bank Account
3. Link Account to Customer
4. Record Transaction
5. Issue Card
6. Assign Card to Customer
7. Link Card to Account
8. View All Customers
9. View All Bank Accounts
10. View All Transactions
11. View All Cards
12. View Customer Details
0. Exit

## Entity Summary

### Customer
Fields:
- customerId (PK)
- fullName
- email (unique)
- phone

Relationships:
- One-to-One with BankAccount (inverse side)
- Many-to-Many with Card (owning side)

### BankAccount
Fields:
- accountId (PK)
- accountNumber (unique)
- accountType
- balance

Relationships:
- One-to-One with Customer (owning side)
- One-to-Many with Transaction (inverse side)

### Transaction
Fields:
- transactionId (PK)
- txnType
- amount
- txnDate
- description
- referenceNumber (unique)

Relationships:
- Many-to-One with BankAccount (owning side)

### Card
Fields:
- cardId (PK)
- cardNumber (unique)
- cardType
- cardNetwork
- expiryDate
- isActive

Relationships:
- Many-to-Many with Customer (inverse side)
- Many-to-One with BankAccount (unidirectional)

## Data Model (Tables)

### customer
- customer_id (PK)
- full_name
- email (unique)
- phone

### bank_account
- account_id (PK)
- account_number (unique)
- account_type
- balance
- customer_id (FK)

### transaction
- transaction_id (PK)
- txn_type
- amount
- txn_date
- description
- reference_number (unique)
- account_id (FK)

### card
- card_id (PK)
- card_number (unique)
- card_type
- card_network
- expiry_date
- is_active
- account_id (FK)

### customer_card (join table)
- customer_id (FK)
- card_id (FK)

## Sample Workflow (Manual)
1. Add Customer
2. Create Bank Account
3. Link Account to Customer
4. Issue Card
5. Link Card to Account
6. Assign Card to Customer
7. Record Transaction
8. View all data

## JPA Notes
- All entities include a no-arg constructor.
- Use helper methods to keep both sides of bidirectional relationships in sync.
- Use `BigDecimal` for monetary values.
- `hibernate.hbm2ddl.auto=update` creates/updates the schema automatically.

## Troubleshooting

### Driver not found (org.postgresql.Driver)
- Rebuild with Maven and ensure the PostgreSQL driver is on classpath.
- In IntelliJ: Maven tool window -> Reload All Maven Projects.

### Connection refused
- Ensure PostgreSQL service is running on port 5432.
- Verify `postgres` user and password in `persistence.xml`.

### Database does not exist
- Create `fintechdb` using `psql` or pgAdmin.

## Quick Commands
Build:
```bash
mvn clean install
```

Run:
```bash
mvn exec:java -Dexec.mainClass="com.fintech.main.FintechApp"
```

Create DB (PowerShell):
```powershell
$env:PGPASSWORD = "root"
& "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "CREATE DATABASE fintechdb;"
```
