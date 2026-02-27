# 🚀 Advanced Java Practice Repository

### Capgemini LPU – Backend Development Track

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![JUnit5](https://img.shields.io/badge/JUnit-5-green)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-blue)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-lightgrey)
![Status](https://img.shields.io/badge/Project-Active-success)

---

## 📌 Overview

This repository represents my **hands-on Advanced Java training** completed during the Capgemini LPU program.

It contains multiple independent Maven-based modules focused on mastering:

* Low-level database connectivity (JDBC)
* ORM with JPA & Hibernate
* Clean DAO architecture
* Unit testing with JUnit 4 & 5
* Parameterized testing techniques
* Backend design best practices

This workspace demonstrates practical implementation of backend engineering fundamentals used in enterprise applications.

---

## 🛠 Tech Stack

* **Java 17**
* **Maven**
* **PostgreSQL**
* **JUnit 4 & JUnit 5**
* **Mockito**
* **JPA**
* **Hibernate ORM**
* **Eclipse IDE**

---

## 📂 Repository Structure

```id="struct91"
capgemini_lpu_advance_java/
│
├── learnjdbc/
├── basicsOfJdbc/
├── Junit_Testing/
├── Hibernate_basics/
├── basics_hibernate/
└── .metadata/
```

Each folder is a standalone Maven project.

---

# 📚 Modules & Learning Highlights

---

## 🔹 1. JDBC CRUD Implementation (`learnjdbc`)

**Objective:**
Understand low-level database interaction using JDBC.

**Key Concepts:**

* DriverManager
* Connection handling
* PreparedStatement
* CRUD operations
* ResultSet processing
* Resource management

**Skills Demonstrated:**

* Manual SQL execution
* Secure query handling
* Database connectivity setup

---

## 🔹 2. Core Java + JUnit Testing (`basicsOfJdbc`)

**Objective:**
Strengthen Java logic building with unit testing.

**Includes:**

* Calculator utility methods
* Employee validation logic
* Exception handling
* Assertion testing

**Testing Features:**

* JUnit 5
* Assertion APIs
* Edge case validation

---

## 🔹 3. Parameterized Testing (`Junit_Testing`)

**Objective:**
Implement data-driven testing strategies.

**Annotations Used:**

* `@ValueSource`
* `@CsvSource`
* `@CsvFileSource`

**Highlights:**

* External CSV-based testing
* Multiple dataset validation
* Clean test structure

---

## 🔹 4. Hibernate ORM with JPA (`Hibernate_basics`)

**Objective:**
Understand Object Relational Mapping fundamentals.

**Concepts Covered:**

* Entity mapping
* Persistence unit configuration
* Transaction lifecycle
* JPQL queries
* CRUD operations using EntityManager

---

## 🔹 5. DAO Architecture Implementation (`basics_hibernate`)

**Objective:**
Apply clean architecture principles.

**Structure:**

* Entity layer
* DAO layer
* Service-style execution
* Unit testing DAO operations

**Professional Concepts:**

* Separation of concerns
* Maintainable structure
* Scalable design pattern

---

# 🧪 How to Run

Navigate inside any module:

```bash
mvn clean test
```

Build without tests:

```bash
mvn clean package -DskipTests
```

---

# 🗄 Database Requirements

For JDBC & Hibernate modules:

* PostgreSQL installed and running
* Database: `school`
* Required tables created
* Credentials updated in code or `persistence.xml`

---

# 🎯 What This Repository Demonstrates

✔ Strong understanding of Java backend fundamentals
✔ Hands-on database integration
✔ ORM expertise using Hibernate
✔ Clean architecture using DAO pattern
✔ Professional unit testing practices
✔ Modular project organization

---

# 💼 Why This Project Matters

This repository reflects:

* Real backend development training
* Industry-aligned coding practices
* Clean project structuring
* Test-driven mindset
* Production-oriented architecture thinking

It represents practical preparation for backend developer roles.

---

# 👨‍💻 Author

**Shubham Kumar**
Advanced Java Trainee 
