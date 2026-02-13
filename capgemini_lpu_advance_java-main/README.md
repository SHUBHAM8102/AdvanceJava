
# 📌 Capgemini LPU – Advanced Java Practice Repository

Welcome to this **multi-module Java learning workspace**, created during the Advanced Java journey at **Capgemini LPU**.

This repository contains multiple independent Maven projects focused on mastering key concepts such as:

* JDBC and CRUD operations
* JPA/Hibernate ORM fundamentals
* Unit Testing using JUnit 4 & JUnit 5
* Parameterized Testing techniques

---

# 🚀 Learning Modules Included

This repo is organized into different practice projects:

| Module Name          | Focus Area                                      |
| -------------------- | ----------------------------------------------- |
| **learnjdbc**        | JDBC basics + PostgreSQL CRUD                   |
| **basicsOfJdbc**     | Core Java utilities + JUnit 5 testing           |
| **Junit_Testing**    | Parameterized tests using multiple data sources |
| **Hibernate_basics** | JPA/Hibernate CRUD with Student entity          |
| **basics_hibernate** | DAO-based Hibernate CRUD with Product entity    |

---

# 🗂️ Current Repository Structure

```
capgemini_lpu_advance_java/
│
├── learnjdbc/
│   ├── src/main/java/com/connectdatabase/
│   ├── pom.xml
│   ├── target/
│   └── Eclipse config files
│
├── basicsOfJdbc/
│   ├── src/main/java/com/prac/
│   ├── src/test/java/com/prac/
│   ├── pom.xml
│   ├── target/
│   └── Eclipse config files
│
├── Junit_Testing/
│   ├── src/main/java/com/connectdatabase/
│   ├── src/test/java/com/learnjdbc/
│   ├── src/test/resources/
│   ├── pom.xml
│   ├── target/
│   └── Eclipse config files
│
├── Hibernate_basics/
│   ├── src/main/java/com/practice/
│   ├── src/main/resources/META-INF/
│   ├── pom.xml
│   ├── target/
│   └── Eclipse config files
│
├── basics_hibernate/
│   ├── src/main/java/com/product/
│   ├── src/main/resources/META-INF/
│   ├── src/test/java/com/basics_hibernate/
│   ├── pom.xml
│   ├── target/
│   └── Eclipse config files
│
└── .metadata/
```

---

# 📚 Module-Wise Explanation

---

## 1️⃣ learnjdbc – JDBC CRUD Practice

### 🎯 Goal

Learn low-level database interaction using **JDBC** with PostgreSQL.

### Concepts Covered

* Driver loading
* DB connection setup
* Insert / Update / Delete operations
* Fetching records from `student` table
* Using `PreparedStatement`

### Typical Workflow

```java
Class.forName(...)
DriverManager.getConnection(...)
Execute SQL queries
Close connection
```

📌 Note: Credentials are currently hardcoded (`localhost:5432/school`).

---

## 2️⃣ basicsOfJdbc – Core Java + Unit Testing

### 🎯 Goal

Practice Java utility methods along with **JUnit 5 testing**.

### Key Classes

* `Calculator.java`

  * add, factorial, palindrome, reverse, division
* `Employee.java`

  * age validation, department checks
* `CalculatorTest.java`

  * assertion practice + exception handling

This module strengthens **Java logic + testing skills**.

---

## 3️⃣ Junit_Testing – Parameterized Testing Module

### 🎯 Goal

Understand data-driven testing with JUnit.

### Key Features

* `@ValueSource`
* `@CsvSource`
* `@CsvFileSource`

### Files Included

* `Program.java` – palindrome + addition
* `EvenOrodd.java` – even/odd checker
* `capgemini.csv` – external test dataset

Perfect module for mastering **Parameterized Tests**.

---

## 4️⃣ Hibernate_basics – JPA/Hibernate CRUD

### 🎯 Goal

Learn ORM basics using **EntityManager**.

### Important Files

* `Student.java` – Entity mapped to `student_info`
* Demo classes:

  * Insert
  * Fetch
  * Update
  * Delete
  * JPQL Query (typo exists currently)

### Teaches

* Persistence Unit setup
* Transaction lifecycle
* CRUD operations using JPA

---

## 5️⃣ basics_hibernate – Hibernate with DAO Pattern

### 🎯 Goal

Practice clean architecture using **DAO layer**.

### Key Components

* `Product.java` – mapped entity
* `ProductDao.java` – CRUD methods
* `Main.java` – runner class
* `ProductDaoTest.java` – unit test setup

This module introduces **separation of concerns**.

---

# ✅ Recommended GitHub Folder Structure (Professional)

To make this repo cleaner for interviews and GitHub:

```
capgemini_lpu_advance_java/
│
├── README.md
├── .gitignore
├── pom.xml   (parent multi-module)
│
├── docs/
│   ├── setup.md
│   └── architecture.md
│
├── modules/
│   ├── jdbc-learn/
│   ├── core-java-testing/
│   ├── junit-parameterized/
│   ├── hibernate-basics/
│   └── hibernate-dao/
│
└── scripts/
    ├── init-db.sql
    └── run-tests.sh
```

### Why Better?

✔ Organized modules
✔ Clean top-level structure
✔ Separate docs and scripts
✔ Easy future expansion (Spring Boot etc.)

---

# ✅ Suggested `.gitignore`

```gitignore
# Maven build
**/target/

# Eclipse files
**/.classpath
**/.project
**/.settings/
.metadata/

# IntelliJ files
.idea/
*.iml

# OS junk
.DS_Store
Thumbs.db
```

---

# ⚙️ Running Any Module

Go inside a module folder and run:

```bash
mvn clean test
```

Or build without tests:

```bash
mvn clean package -DskipTests
```

---

# 🛠 Requirements for JDBC/Hibernate Modules

Before running:

* PostgreSQL server must be running
* Database `school` should exist
* Required tables must be created
* Credentials in code/persistence.xml must match your system
