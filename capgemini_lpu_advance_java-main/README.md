# Capgemini LPU Advance Java Practice Repository

This repository is a **multi-module learning workspace** for Java topics covered during an advanced Java journey:

- JDBC basics and CRUD operations
- JPA/Hibernate ORM basics
- Unit testing with JUnit 4 & JUnit 5 (including parameterized tests)

It currently contains multiple independent Maven projects created for practice and experimentation.

---

## 📌 Repository Overview

This repo includes these learning projects:

1. `learnjdbc` – JDBC connection + CRUD with PostgreSQL (`student` table)
2. `basicsOfJdbc` – Java utility methods + JUnit 5 test practice
3. `Junit_Testing` – Parameterized tests (`@ValueSource`, `@CsvSource`, `@CsvFileSource`)
4. `Hibernate_basics` – JPA/Hibernate CRUD using `Student` entity
5. `basics_hibernate` – DAO-based Hibernate CRUD for `Product` entity

---

## 🗂️ Current Folder Structure (As Present in Repo)

```text
capgemini_lpu_advance_java/
├── learnjdbc/
│   ├── src/main/java/com/connectdatabase/   # JDBC CRUD demo classes
│   ├── pom.xml                              # PostgreSQL + JUnit Jupiter API
│   ├── target/                              # Compiled build output (generated)
│   └── .settings/.project/.classpath        # Eclipse metadata
├── basicsOfJdbc/
│   ├── src/main/java/com/prac/              # Calculator + Employee examples
│   ├── src/test/java/com/prac/              # JUnit test class
│   ├── pom.xml                              # JUnit Jupiter API dependency
│   ├── target/                              # Compiled build output (generated)
│   └── .settings/.project/.classpath        # Eclipse metadata
├── Junit_Testing/
│   ├── src/main/java/com/connectdatabase/   # Business logic for tests
│   ├── src/test/java/com/learnjdbc/         # Parameterized test class
│   ├── src/test/resources/                  # CSV test data
│   ├── pom.xml                              # JUnit 4 + JUnit 5 params
│   ├── target/                              # Compiled build output (generated)
│   └── .settings/.project/.classpath        # Eclipse metadata
├── Hibernate_basics/
│   ├── src/main/java/com/practice/          # Student entity + CRUD demo classes
│   ├── src/main/resources/META-INF/         # persistence.xml
│   ├── pom.xml                              # Hibernate + PostgreSQL dependencies
│   ├── target/                              # Compiled build output (generated)
│   └── .settings/.project/.classpath        # Eclipse metadata
├── basics_hibernate/
│   ├── src/main/java/com/product/           # Product entity + ProductDao + Main
│   ├── src/main/resources/META-INF/         # persistence.xml
│   ├── src/test/java/com/basics_hibernate/  # ProductDao test class
│   ├── pom.xml                              # Hibernate + PostgreSQL + JUnit
│   ├── target/                              # Compiled build output (generated)
│   └── .settings/.project/.classpath        # Eclipse metadata
└── .metadata/                               # Local Eclipse workspace metadata
```

---

## 📚 Detailed Explanation of Each Project

### 1) `learnjdbc`
Purpose: Understand low-level JDBC operations using PostgreSQL.

Contains classes that demonstrate:
- Driver loading and DB connection
- Insert/update/delete using `Statement` / `PreparedStatement`
- Fetching rows from `student` table
- Fetching by user input (`id`)

Typical flow in these classes:
1. `Class.forName("org.postgresql.Driver")`
2. `DriverManager.getConnection(...)`
3. Execute SQL (`insert`, `update`, `delete`, `select`)
4. Print result / close connection

> Notes: DB credentials are currently hardcoded (`localhost:5432/school`, user `postgres`, password `root`).

---

### 2) `basicsOfJdbc`
Purpose: Practice Java methods and unit testing concepts.

Key files:
- `Calculator.java` – add, reverse string, factorial, palindrome reverse, division
- `Employee.java` – validation methods for age and department
- `CalculatorTest.java` – tests for string reverse, factorial, palindrome, validation, exception handling

This project is useful for understanding **core Java + JUnit 5 assertions**.

---

### 3) `Junit_Testing`
Purpose: Learn parameterized testing patterns.

Key files:
- `Program.java` – palindrome checker + add method
- `EvenOrodd.java` – returns `even` or `odd`
- `ProgrameTest.java` – demonstrates:
  - `@ValueSource`
  - `@CsvSource`
  - `@CsvFileSource`
- `capgemini.csv` – external test dataset for parity tests

This module is focused on **data-driven testing**.

---

### 4) `Hibernate_basics`
Purpose: Learn JPA/Hibernate CRUD with an entity model.

Key files:
- `Student.java` – JPA entity mapped to `student_info`
- `Demo.java` – insert
- `Demo1.java` – fetch by primary key
- `Demo2.java` – delete
- `Demo3.java` – update
- `Demo4.java` – JPQL query (has a query string typo currently)
- `persistence.xml` – persistence unit `postgres` + DB configuration

This module teaches **EntityManager lifecycle + transaction flow**.

---

### 5) `basics_hibernate`
Purpose: Practice Hibernate with DAO pattern.

Key files:
- `Product.java` – entity mapped to `product`
- `ProductDao.java` – insert/find/update/delete methods
- `Main.java` – sample runner for DAO calls
- `ProductDaoTest.java` – basic DAO test setup using JUnit
- `persistence.xml` – DB + hibernate properties

This module introduces **separation of concerns** by moving DB logic to DAO.

---

## 🧭 Best Folder Structure (Recommended for GitHub)

To make this repo cleaner and professional for GitHub, you can use the structure below:

```text
capgemini_lpu_advance_java/
├── README.md
├── .gitignore
├── pom.xml                         # parent aggregator POM (optional but recommended)
├── docs/
│   ├── setup.md                    # DB setup, prerequisites, run instructions
│   └── architecture.md             # module-wise explanation
├── modules/
│   ├── jdbc-learn/
│   ├── core-java-testing/
│   ├── junit-parameterized/
│   ├── hibernate-basics/
│   └── hibernate-dao/
└── scripts/
    ├── init-db.sql
    └── run-all-tests.sh
```

### Why this structure is better
- Keeps learning modules grouped under one `modules/` folder
- Makes top-level repo clean and interview-ready
- Separates **documentation**, **code**, and **scripts**
- Easy to scale when adding Spring Boot, servlet, or microservice modules later

---

## ✅ Suggested `.gitignore` (Important)

Since this is a Java + Maven + Eclipse workspace, ignore generated and local IDE files:

```gitignore
# Maven
**/target/

# Eclipse
**/.classpath
**/.project
**/.settings/
.metadata/

# IntelliJ (if used later)
.idea/
*.iml

# OS files
.DS_Store
Thumbs.db
```

---

## ⚙️ How to Run Any Module

Inside a module folder:

```bash
mvn clean test
```

or to build without tests:

```bash
mvn clean package -DskipTests
```

For JDBC/Hibernate modules, ensure:
- PostgreSQL server is running
- Database `school` exists
- Required tables (`student`, `student_info`, `product`) are available
- Credentials in code / `persistence.xml` are correct for your environment

---

## 🚀 Future Improvements

- Add a parent multi-module Maven `pom.xml`
- Externalize DB credentials using properties/env vars
- Add SQL schema scripts under `scripts/`
- Improve test reliability (avoid depending on fixed DB records)
- Rename inconsistent packages/classes for clean naming conventions
- Fix JPQL typo in `Hibernate_basics` demo query

---

## 👨‍💻 Author

**Arjit**  
Advanced Java practice repository for Capgemini/LPU learning path.

If you'd like, I can also generate:
1. a **ready-to-use parent `pom.xml`** for all modules,
2. a **professional `.gitignore`** file,
3. and a **`docs/setup.md` with SQL table creation scripts**.
