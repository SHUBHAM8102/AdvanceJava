# 🔗 URL Shortener Service — Spring Boot REST API

A production-style **URL Shortener Backend** built with **Spring Boot**, **Spring Data JPA**, **PostgreSQL**, **Hibernate**, and **Swagger/OpenAPI**. Users can submit long URLs and receive short, unique aliases that redirect to the original address.

---

## 📑 Table of Contents

1. [Technologies Used](#-technologies-used)
2. [Prerequisites](#-prerequisites)
3. [Project Structure](#-project-structure)
4. [Database Setup](#-database-setup)
5. [Application Configuration](#-application-configuration)
6. [How to Run the Application](#-how-to-run-the-application)
7. [Architecture & Layered Design](#-architecture--layered-design)
8. [Entity & Database Mapping](#-entity--database-mapping)
9. [Repository Layer](#-repository-layer)
10. [Service Layer & Business Logic](#-service-layer--business-logic)
11. [Controller Layer & REST Endpoints](#-controller-layer--rest-endpoints)
12. [Data Transfer Objects (DTOs)](#-data-transfer-objects-dtos)
13. [Exception Handling](#-exception-handling)
14. [Swagger / OpenAPI Documentation](#-swagger--openapi-documentation)
15. [HTTP Status Codes Used](#-http-status-codes-used)
16. [Testing with Swagger UI / Postman](#-testing-with-swagger-ui--postman)
17. [Design Questions & Answers](#-design-questions--answers)
18. [PostgreSQL Table DDL](#-postgresql-table-ddl)

---

## 🛠 Technologies Used

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 | Programming Language |
| Spring Boot | 3.4.3 | Application Framework |
| Spring Data JPA | (via Spring Boot) | Database ORM & Repository |
| Hibernate | (via Spring Boot) | JPA Implementation / ORM |
| PostgreSQL | 18 | Relational Database |
| Lombok | (via Spring Boot) | Boilerplate Code Reduction |
| Springdoc OpenAPI | 2.8.4 | Swagger UI / API Documentation |
| Maven | 3.9.12 | Build Tool & Dependency Management |
| HikariCP | (via Spring Boot) | Database Connection Pooling |

---

## ✅ Prerequisites

Ye cheezein tumhare system mein installed honi chahiye application chalane se pehle:

1. **Java JDK 21** — `C:\Program Files\Java\jdk-21` ya koi bhi path.
2. **PostgreSQL 18** (ya koi bhi compatible version) — installed aur running hona chahiye.
3. **Maven** — project build karne ke liye (ya IntelliJ ka built-in Maven use karo).
4. **IntelliJ IDEA** (Recommended) — ya koi bhi Java IDE.

### Verify Installations

```bash
java -version          # Should show java 21.x.x
psql --version         # Should show psql (PostgreSQL) 18.x
mvn -version           # Should show Apache Maven 3.x.x
```

---

## 📂 Project Structure

```
28_Feb_URL_Shortener_Service/
├── pom.xml                              ← Maven dependencies & build config
├── README.md                            ← This file
└── src/
    └── main/
        ├── java/pom/capgemini/
        │   ├── Main.java                ← Spring Boot entry point (@SpringBootApplication)
        │   ├── config/
        │   │   └── OpenApiConfig.java   ← Swagger/OpenAPI global configuration
        │   ├── controller/
        │   │   └── UrlMappingController.java  ← REST API endpoints (6 endpoints)
        │   ├── dto/
        │   │   ├── UrlRequestDto.java   ← Input DTO for POST /api/shorten
        │   │   ├── UrlResponseDto.java  ← Output DTO for URL responses
        │   │   ├── StatsDto.java        ← Output DTO for statistics
        │   │   └── ErrorResponseDto.java← Error response DTO for 4xx/5xx
        │   ├── entity/
        │   │   └── UrlMapping.java      ← JPA Entity mapped to PostgreSQL table
        │   ├── exception/
        │   │   ├── ShortCodeNotFoundException.java     ← Custom 404 exception
        │   │   ├── ShortCodeAlreadyExistsException.java← Custom 409 exception
        │   │   └── GlobalExceptionHandler.java         ← @RestControllerAdvice
        │   ├── repository/
        │   │   └── UrlMappingRepository.java  ← JpaRepository + custom queries
        │   └── service/
        │       └── UrlMappingService.java     ← All business logic
        └── resources/
            └── application.properties   ← DB config, server port, Hibernate settings
```

---

## 🗄 Database Setup

### Step 1: PostgreSQL Start karo

Pehle ensure karo ki PostgreSQL service tumhare system pe running hai. Windows pe:
- `Services` app kholo → `postgresql-x64-18` service dhundho → `Start` karo agar running nahi hai.

### Step 2: Database Create karo

Command Prompt ya PowerShell kholo aur ye command run karo:

```sql
psql -U postgres -c "CREATE DATABASE url_shortener_db;"
```

Password puchega to `root` enter karo.

**Ya phir pgAdmin GUI se:**
1. pgAdmin kholo
2. `Servers` > `PostgreSQL 18` pe right-click → Connect (password: `root`)
3. `Databases` pe right-click → `Create` → `Database`
4. Name: `url_shortener_db` → Save

### Step 3: Verify karo

```sql
psql -U postgres -c "\l"
```

Output mein `url_shortener_db` dikhna chahiye.

> **Note:** Table manually banane ki zaroorat NAHI hai! Hibernate automatically `url_mapping` table create kar dega kyunki `spring.jpa.hibernate.ddl-auto=update` set hai.

---

## ⚙ Application Configuration

`src/main/resources/application.properties` file mein ye settings configured hain:

```properties
# PostgreSQL Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener_db
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Connection Pool (HikariCP)
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.pool-name=UrlShortenerHikariPool

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Server Port
server.port=8088
```

### Har Property ka Matlab:

| Property | Kya karta hai |
|---|---|
| `spring.datasource.url` | PostgreSQL database ka JDBC connection URL (localhost, port 5432, database name `url_shortener_db`) |
| `spring.datasource.username` | Database username → `postgres` |
| `spring.datasource.password` | Database password → `root` |
| `spring.jpa.hibernate.ddl-auto=update` | Hibernate automatically table create/update karega Java entity ke basis pe. App restart pe existing data delete NAHI hoga. (`create` = har baar nayi table, `create-drop` = app stop pe table drop, `validate` = sirf check karega, change nahi karega) |
| `spring.jpa.show-sql=true` | Console pe SQL queries print hogi — debugging ke liye useful |
| `spring.jpa.properties.hibernate.format_sql=true` | SQL queries formatted (readable) dikhegi |
| `spring.datasource.hikari.minimum-idle=2` | Connection pool mein minimum 2 idle connections rahenge |
| `spring.datasource.hikari.maximum-pool-size=10` | Maximum 10 database connections ek saath |
| `server.port=8088` | Application port 8088 pe chalega (default 8080 ke bajaye) |

### ⚠ Environment Variables

Agar tumhara PostgreSQL password `root` nahi hai, to `application.properties` mein `spring.datasource.password` change karo apne password se.

---

## 🚀 How to Run the Application

### Method 1: IntelliJ IDEA se (Recommended)

1. IntelliJ mein project kholo
2. Pehli baar `pom.xml` pe right-click → `Maven` → `Reload Project` (dependencies download hongi)
3. `src/main/java/pom/capgemini/Main.java` file kholo
4. `Main` class ke paas green ▶ play button pe click karo → `Run 'Main'`
5. Console mein dekhoge:
   ```
   Tomcat started on port 8088
   Started Main in X.XX seconds
   ```
6. Browser mein kholo: **http://localhost:8088/swagger-ui/index.html**

### Method 2: Command Line / Terminal se

```bash
cd C:\Users\Shashwat\OneDrive\Desktop\SpringBoot\28_Feb_URL_Shortener_Service
mvn clean compile
mvn spring-boot:run
```

### Method 3: JAR Build karke

```bash
mvn clean package -DskipTests
java -jar target/28_Feb_URL_Shortener_Service-1.0-SNAPSHOT.jar
```

---

## 🏗 Architecture & Layered Design

Application **strict layered architecture** follow karta hai. Koi bhi layer apne se neeche wali layer ko bypass nahi karti:

```
┌─────────────────────────────────────────┐
│           CLIENT (Browser/Postman)       │
└──────────────────┬──────────────────────┘
                   │ HTTP Request
                   ▼
┌─────────────────────────────────────────┐
│       CONTROLLER LAYER (Blue)            │
│  UrlMappingController.java               │
│  - REST endpoints define karta hai       │
│  - @Valid se input validate karta hai     │
│  - ResponseEntity return karta hai       │
│  - Koi business logic NAHI hai yahan     │
└──────────────────┬──────────────────────┘
                   │ Method Call
                   ▼
┌─────────────────────────────────────────┐
│        SERVICE LAYER (Green)             │
│  UrlMappingService.java                  │
│  - Saari business logic yahan hai        │
│  - Short code generation                 │
│  - Click count increment                 │
│  - Entity ↔ DTO conversion              │
│  - @Transactional for DB writes          │
└──────────────────┬──────────────────────┘
                   │ Method Call
                   ▼
┌─────────────────────────────────────────┐
│       REPOSITORY LAYER (Orange)          │
│  UrlMappingRepository.java               │
│  - JpaRepository extend karta hai        │
│  - CRUD operations automatically milte   │
│  - Custom query methods defined          │
│  - @Query JPQL for complex queries       │
└──────────────────┬──────────────────────┘
                   │ SQL Query
                   ▼
┌─────────────────────────────────────────┐
│         DATABASE (PostgreSQL)            │
│  Table: url_mapping                      │
│  Columns: id, original_url, short_code,  │
│           click_count, created_at,       │
│           updated_at                     │
└─────────────────────────────────────────┘
```

### Cross-Cutting Concerns:
- **Exception Handling** → `GlobalExceptionHandler.java` (`@RestControllerAdvice`)
- **Swagger Config** → `OpenApiConfig.java` (`@Configuration`)
- **DTOs** → Entity directly expose nahi hoti, DTO objects use hote hain

---

## 🗃 Entity & Database Mapping

### `UrlMapping.java` → `url_mapping` table

| Java Field | Annotation | DB Column | DB Type | Constraint | Explanation |
|---|---|---|---|---|---|
| `id` | `@Id` + `@GeneratedValue(IDENTITY)` | `id` | `BIGSERIAL` | PRIMARY KEY, AUTO INCREMENT | Har record ka unique identifier. PostgreSQL khud generate karta hai. |
| `originalUrl` | `@Column(nullable=false, length=2048)` | `original_url` | `VARCHAR(2048)` | NOT NULL | Original long URL store hota hai. 2048 max length rakhi hai long URLs ke liye. |
| `shortCode` | `@Column(nullable=false, unique=true, length=8)` | `short_code` | `VARCHAR(8)` | NOT NULL, UNIQUE | 6-8 character alphanumeric code. `unique=true` se duplicate short codes nahi ban sakte. |
| `clickCount` | `@Column(nullable=false)` + `@Builder.Default` | `click_count` | `BIGINT` | NOT NULL, DEFAULT 0 | Kitni baar short URL access hua — default value 0. |
| `createdAt` | `@CreationTimestamp` + `@Column(updatable=false)` | `created_at` | `TIMESTAMP` | NOT NULL, IMMUTABLE | Record kab bana — Hibernate automatically set karta hai INSERT pe. `updatable=false` se ye field UPDATE mein kabhi change nahi hoga. |
| `updatedAt` | `@UpdateTimestamp` | `updated_at` | `TIMESTAMP` | NOT NULL | Record kab last modify hua — Hibernate automatically refresh karta hai har UPDATE pe. |

### Kyun `@UniqueConstraint` use NAHI kiya `@Table` mein?

- `@Column(unique = true)` already single column pe UNIQUE constraint lagata hai DDL mein.
- Agar `@Table(uniqueConstraints = @UniqueConstraint(...))` bhi laga dete, to Hibernate **2 duplicate UNIQUE constraints** generate karta — redundant aur confusing.
- `@UniqueConstraint` in `@Table` sirf tab use karo jab **composite unique constraint** chahiye (multiple columns ka combination unique hona chahiye), jaise `(user_id + short_code)`.

### `@CreationTimestamp` vs `@UpdateTimestamp`

- `@CreationTimestamp` → Sirf **ek baar** set hota hai jab entity pehli baar INSERT hoti hai. Phir kabhi change nahi hota.
- `@UpdateTimestamp` → **Har UPDATE** pe automatically current timestamp se refresh hota hai. Jab click count badhta hai to `updatedAt` bhi update ho jaata hai.

### `LocalDateTime` vs `Instant`

- **LocalDateTime** → Date + Time bina timezone ke. Simple hai, single timezone apps ke liye sufficient.
- **Instant** → UTC epoch time store karta hai. Global/multi-timezone apps ke liye better.
- Is project mein `LocalDateTime` use kiya hai kyunki single server pe chal raha hai.

---

## 📦 Repository Layer

### `UrlMappingRepository.java`

`JpaRepository<UrlMapping, Long>` extend karta hai — isse automatically ye CRUD methods milte hain:
- `save()`, `findById()`, `findAll()`, `deleteById()`, `count()`, etc.

### Custom Query Methods (Spring Data JPA Method Naming Convention):

| Method | Kya karta hai | Kaise kaam karta hai |
|---|---|---|
| `findByShortCode(String shortCode)` | Short code se UrlMapping dhundhta hai | Spring Data JPA automatically `WHERE short_code = ?` query banata hai method name se |
| `existsByShortCode(String shortCode)` | Check karta hai ki short code exist karta hai ya nahi (boolean) | `SELECT COUNT(*) > 0 FROM url_mapping WHERE short_code = ?` |
| `findByCreatedAtAfter(LocalDateTime dateTime)` | Given date ke baad bani saari entries return karta hai | `WHERE created_at > ?` |

### Custom JPQL Query (`@Query` annotation):

```java
@Query("SELECT u FROM UrlMapping u ORDER BY u.clickCount DESC LIMIT 5")
List<UrlMapping> findTop5MostClickedUrls();
```

- Ye JPQL (Java Persistence Query Language) hai — SQL jaisi language but Java entities pe kaam karti hai.
- `UrlMapping` = Java class name (table name nahi).
- `u.clickCount` = Java field name (column name nahi).
- Top 5 sabse zyada clicked URLs return karta hai.

---

## ⚡ Service Layer & Business Logic

### `UrlMappingService.java`

Ye class saari business logic handle karti hai. Controller directly entity objects return nahi karta — DTO objects use hote hain.

### Short Code Generation Algorithm:

```
Character Set: a-z, A-Z, 0-9 (62 characters total)
Length: Random between 6 and 8 characters
Method: SecureRandom se ek ek character pick karta hai
Collision Handling: Agar generated code already exist karta hai DB mein,
                    to retry karta hai — maximum 10 attempts
```

**Possible combinations:**
- 6 chars: 62⁶ = ~56.8 billion
- 7 chars: 62⁷ = ~3.5 trillion
- 8 chars: 62⁸ = ~218 trillion

Collision chance extremely low hai.

### Design Decision — Same URL Submit karne pe:

> Agar same long URL dubara submit karo (bina custom alias ke), to **NAYI short code generate hoti hai**. Ek URL ke multiple short codes ho sakte hain. Ye design flexibility deta hai — alag campaigns ke liye alag short codes track kar sakte ho.

### Service Methods:

| Method | Kya karta hai | Annotation |
|---|---|---|
| `createShortUrl(UrlRequestDto)` | Nayi short URL banata hai. Custom alias diya to use karta hai, nahi to random generate karta hai. | `@Transactional` |
| `resolveAndTrackClick(String shortCode)` | Short code se original URL dhundhta hai, click count +1 karta hai, original URL return karta hai. | `@Transactional` |
| `getStats(String shortCode)` | Short code ki statistics return karta hai (clicks, timestamps). | — |
| `getAllUrls(int page, int size)` | Saari URL mappings paginated list mein return karta hai. | — |
| `deleteByShortCode(String shortCode)` | Short code ki mapping delete karta hai. Nahi mili to 404 exception. | `@Transactional` |
| `getTopUrls()` | Top 5 sabse zyada clicked URLs return karta hai. | — |

### `@Transactional` kyun lagaya?

- Jo methods database mein **write** karte hain (INSERT, UPDATE, DELETE) unpe `@Transactional` lagaya hai.
- Isse agar beech mein koi error aaye to saare changes **rollback** ho jaate hain — data consistent rehta hai.
- `resolveAndTrackClick()` mein click count increment aur save **ek hi transaction** mein hota hai — atomic operation.

### Constructor Injection (No `@Autowired` on fields):

```java
private final UrlMappingRepository urlMappingRepository;

public UrlMappingService(UrlMappingRepository urlMappingRepository) {
    this.urlMappingRepository = urlMappingRepository;
}
```

- Field pe `@Autowired` lagana **anti-pattern** hai — testing mein problem hota hai.
- Constructor injection use karne se dependency `final` rakh sakte ho — immutable aur safe.
- Spring Boot automatically constructor injection kar leta hai jab single constructor ho.

---

## 🌐 Controller Layer & REST Endpoints

### `UrlMappingController.java`

6 REST endpoints expose karta hai. Har method `ResponseEntity` return karta hai proper HTTP status code ke saath.

### All 6 Endpoints:

---

### 1️⃣ `POST /api/shorten` — Create Short URL

**Request Body:**
```json
{
  "originalUrl": "https://www.google.com/search?q=spring+boot+tutorial",
  "customAlias": "google1"    // Optional — 6-8 characters
}
```

**Success Response (201 Created):**
```json
{
  "shortCode": "google1",
  "shortUrl": "http://localhost:8088/api/google1",
  "originalUrl": "https://www.google.com/search?q=spring+boot+tutorial",
  "clickCount": 0,
  "createdAt": "2026-02-28T21:15:30.123456",
  "updatedAt": "2026-02-28T21:15:30.123456"
}
```

**Error — Blank URL (400 Bad Request):**
```json
{
  "timestamp": "2026-02-28T21:16:00.000000",
  "status": 400,
  "message": "Validation failed",
  "fieldErrors": {
    "originalUrl": "Original URL must not be blank"
  }
}
```

**Error — Alias Already Exists (409 Conflict):**
```json
{
  "timestamp": "2026-02-28T21:17:00.000000",
  "status": 409,
  "message": "Short code 'google1' already exists",
  "fieldErrors": null
}
```

---

### 2️⃣ `GET /api/{shortCode}` — Redirect to Original URL

**Example:** `GET http://localhost:8088/api/google1`

**Success (302 Found):**
- Response body empty hota hai.
- `Location` header set hota hai: `Location: https://www.google.com/search?q=spring+boot+tutorial`
- Browser automatically redirect ho jaata hai original URL pe.
- Click count +1 ho jaata hai.

**Error — Short Code Not Found (404):**
```json
{
  "timestamp": "2026-02-28T21:18:00.000000",
  "status": 404,
  "message": "Short code 'xyz123' not found",
  "fieldErrors": null
}
```

> **Note:** Browser mein test karo to directly redirect ho jaayega. Postman mein "Follow Redirects" off karo 302 response dekhne ke liye.

---

### 3️⃣ `GET /api/stats/{shortCode}` — Get Click Statistics

**Example:** `GET http://localhost:8088/api/stats/google1`

**Success Response (200 OK):**
```json
{
  "shortCode": "google1",
  "originalUrl": "https://www.google.com/search?q=spring+boot+tutorial",
  "clickCount": 5,
  "createdAt": "2026-02-28T21:15:30.123456",
  "lastAccessedAt": "2026-02-28T21:20:00.654321"
}
```

---

### 4️⃣ `GET /api/urls?page=0&size=10` — List All URLs (Paginated)

**Query Parameters:**
- `page` → Page number (0-based, default: 0)
- `size` → Items per page (default: 10)

**Example:** `GET http://localhost:8088/api/urls?page=0&size=5`

**Success Response (200 OK):**
```json
{
  "content": [
    {
      "shortCode": "google1",
      "shortUrl": "http://localhost:8088/api/google1",
      "originalUrl": "https://www.google.com/...",
      "clickCount": 5,
      "createdAt": "...",
      "updatedAt": "..."
    },
    { ... },
    { ... }
  ],
  "totalElements": 12,
  "totalPages": 3,
  "number": 0,
  "size": 5
}
```

---

### 5️⃣ `DELETE /api/{shortCode}` — Delete a Short URL

**Example:** `DELETE http://localhost:8088/api/google1`

**Success (204 No Content):** Empty body, mapping deleted.

**Error (404 Not Found):** Agar short code exist nahi karta.

---

### 6️⃣ `GET /api/top` — Top 5 Most Clicked URLs

**Example:** `GET http://localhost:8088/api/top`

**Success Response (200 OK):**
```json
[
  {
    "shortCode": "aBcD12",
    "shortUrl": "http://localhost:8088/api/aBcD12",
    "originalUrl": "https://youtube.com",
    "clickCount": 150,
    "createdAt": "...",
    "updatedAt": "..."
  },
  { ... }
]
```

Agar koi entry nahi hai to empty list `[]` return hoti hai (404 nahi).

---

## 📋 Data Transfer Objects (DTOs)

Entity directly API response mein expose nahi hoti. DTOs use hote hain:

### `UrlRequestDto` — Input (POST request body)

| Field | Validation | Description |
|---|---|---|
| `originalUrl` | `@NotBlank`, `@URL` | Long URL — blank nahi ho sakta, valid URL format hona chahiye |
| `customAlias` | `@Size(min=6, max=8)` | Optional — agar user apna custom short code dena chahe |

### `UrlResponseDto` — Output (Create/List/Top responses)

| Field | Description |
|---|---|
| `shortCode` | Generated ya custom short code |
| `shortUrl` | Full short URL (e.g., `http://localhost:8088/api/aBcD12`) |
| `originalUrl` | Original long URL |
| `clickCount` | Total clicks |
| `createdAt` | Kab bana |
| `updatedAt` | Kab last update hua |

### `StatsDto` — Statistics Output

| Field | Description |
|---|---|
| `shortCode` | Short code |
| `originalUrl` | Original URL |
| `clickCount` | Total clicks |
| `createdAt` | Creation timestamp |
| `lastAccessedAt` | Last access/update timestamp |

`StatsDto` alag hai `UrlResponseDto` se kyunki isme `shortUrl` nahi hai aur `lastAccessedAt` naam zyada meaningful hai stats context mein.

### `ErrorResponseDto` — Error Output (4xx/5xx)

| Field | Description |
|---|---|
| `timestamp` | Error kab hua |
| `status` | HTTP status code (404, 409, 400, 500) |
| `message` | Human-readable error message |
| `fieldErrors` | Map of field → error message (validation errors ke liye) |

---

## 🚨 Exception Handling

### `GlobalExceptionHandler.java` — `@RestControllerAdvice`

Ye class globally saari exceptions handle karti hai. Kisi bhi controller mein exception throw ho, ye class pakad leti hai aur proper error response return karti hai.

| Exception | HTTP Status | Kab throw hoti hai |
|---|---|---|
| `ShortCodeNotFoundException` | 404 Not Found | Jab short code database mein nahi milta (resolve, stats, delete) |
| `ShortCodeAlreadyExistsException` | 409 Conflict | Jab user custom alias deta hai jo already exist karti hai |
| `MethodArgumentNotValidException` | 400 Bad Request | Jab `@Valid` validation fail hoti hai (blank URL, invalid format) |
| `Exception` (generic) | 500 Internal Server Error | Koi bhi unexpected error |

### `@RestControllerAdvice` vs `@ControllerAdvice`:

- `@RestControllerAdvice` = `@ControllerAdvice` + `@ResponseBody`
- `@ControllerAdvice` mein manually `@ResponseBody` lagana padta hai har method pe.
- `@RestControllerAdvice` REST APIs ke liye use hota hai (JSON response), `@ControllerAdvice` traditional MVC (view/HTML) ke liye.

### Custom Exceptions:

```java
// ShortCodeNotFoundException.java
public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String shortCode) {
        super("Short code '" + shortCode + "' not found");
    }
}

// ShortCodeAlreadyExistsException.java
public class ShortCodeAlreadyExistsException extends RuntimeException {
    public ShortCodeAlreadyExistsException(String shortCode) {
        super("Short code '" + shortCode + "' already exists");
    }
}
```

---

## 📖 Swagger / OpenAPI Documentation

### Access Swagger UI:

Application chalane ke baad browser mein kholo:

```
http://localhost:8088/swagger-ui/index.html
```

### OpenAPI Config (`OpenApiConfig.java`):

- API title: **URL Shortener Service API**
- Version: **1.0.0**
- Description aur Contact info set hai.

### Controller pe Swagger Annotations:

| Annotation | Kahan use hua | Kya karta hai |
|---|---|---|
| `@Tag` | Controller class pe | Swagger UI mein endpoints ko group karta hai |
| `@Operation` | Har method pe | Summary aur description deta hai endpoint ki |
| `@ApiResponses` / `@ApiResponse` | Har method pe | Possible HTTP responses document karta hai (200, 201, 302, 400, 404, 409) |
| `@Parameter` | Path variables / Query params pe | Parameter ka description aur example deta hai |
| `@RequestBody` (Swagger) | POST method pe | Request body document karta hai |
| `@Schema` | DTO fields pe | Field-level description aur example values Swagger UI mein dikhata hai |
| `@Content` | `@ApiResponse` ke andar | Response body ka schema define karta hai (kaunsa DTO return hoga) |

---

## 📊 HTTP Status Codes Used

| Scenario | HTTP Status | Response Body |
|---|---|---|
| Short URL created successfully | `201 Created` | `UrlResponseDto` |
| Redirect to original URL | `302 Found` | Empty (Location header set) |
| Stats / List / Top fetched | `200 OK` | `StatsDto` / `Page<UrlResponseDto>` / `List<UrlResponseDto>` |
| Delete successful | `204 No Content` | Empty |
| Short code not found | `404 Not Found` | `ErrorResponseDto` |
| Custom alias already taken | `409 Conflict` | `ErrorResponseDto` |
| Validation failure (bad input) | `400 Bad Request` | `ErrorResponseDto` with `fieldErrors` |
| Unexpected server error | `500 Internal Server Error` | `ErrorResponseDto` |

### Redirect Response kaise kaam karta hai:

```java
HttpHeaders headers = new HttpHeaders();
headers.setLocation(URI.create(originalUrl));
return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
```

- `HttpStatus.FOUND` = 302 status code
- `Location` header mein original URL set hota hai
- Browser jab 302 response dekhta hai `Location` header ke saath, to automatically us URL pe redirect ho jaata hai
- `HttpServletResponse.sendRedirect()` use NAHI kiya — `ResponseEntity` exclusively use kiya hai

---

## 🧪 Testing with Swagger UI / Postman

### Swagger UI se Test karo:

1. App start karo
2. `http://localhost:8088/swagger-ui/index.html` kholo
3. Koi bhi endpoint expand karo → `Try it out` → Values fill karo → `Execute`

### Postman se Test karo:

**Step 1 — Create Short URL:**
```
POST http://localhost:8088/api/shorten
Content-Type: application/json

{
  "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
}
```

**Step 2 — Test Redirect (browser mein):**
```
GET http://localhost:8088/api/{shortCode_from_step_1}
```
(Browser mein paste karo — YouTube pe redirect ho jaayega)

**Step 3 — Check Stats:**
```
GET http://localhost:8088/api/stats/{shortCode}
```

**Step 4 — List All:**
```
GET http://localhost:8088/api/urls?page=0&size=10
```

**Step 5 — Top 5:**
```
GET http://localhost:8088/api/top
```

**Step 6 — Delete:**
```
DELETE http://localhost:8088/api/{shortCode}
```

> **Tip:** Postman mein redirect endpoint test karte waqt `Settings` → `Follow Redirects` **OFF** karo, warna 302 response nahi dikhega — directly redirect ho jaayega.

---

## 💡 Design Questions & Answers

### 1. Short code generation algorithm kya hai aur collision kaise handle hota hai?

**Algorithm:** `SecureRandom` use karke 62 alphanumeric characters (a-z, A-Z, 0-9) ke set se randomly 6 to 8 character ka code generate hota hai.

**Collision Handling:** Generate hone ke baad `existsByShortCode()` se check hota hai ki DB mein already exist to nahi karta. Agar exist karta hai to naya code generate karta hai — maximum 10 retries. 10 attempts ke baad bhi unique nahi mila to RuntimeException throw hoti hai. Practically collision chance negligible hai (62⁶ = ~56.8 billion combinations).

### 2. `@UniqueConstraint` inside `@Table` kyun NAHI use kiya?

`@Column(unique = true)` already DDL mein single-column UNIQUE constraint generate karta hai. Agar `@Table(uniqueConstraints = ...)` bhi laga dete to Hibernate **2 duplicate UNIQUE constraints** banata — redundant indexes, wasted storage. `@UniqueConstraint` in `@Table` sirf **composite unique constraints** ke liye use hona chahiye, jaise `@UniqueConstraint(columnNames = {"user_id", "short_code"})`.

### 3. `LocalDateTime` vs `Instant` — difference aur globally deploy karne pe kaunsa choose karein?

- `LocalDateTime` = date + time **bina timezone** ke. `2026-02-28T21:15:30` — isko dekh ke pata nahi chalta kis timezone ka hai.
- `Instant` = UTC epoch time. `2026-02-28T15:45:30Z` — hamesha UTC mein store hota hai.
- **Globally deploy** karne pe `Instant` use karna chahiye kyunki alag timezones ke servers pe `LocalDateTime` inconsistent ho jaayega. PostgreSQL mein `Instant` ke saath `TIMESTAMP WITH TIME ZONE` column type use karna chahiye.

### 4. EAGER vs LAZY fetch types — kahan important hoga?

- **EAGER** = Related entities turant load hoti hain (parent ke saath). Simple but performance problem jab bahut saari related entities ho.
- **LAZY** = Related entities tab load hoti hain jab actually access karo. Performance better but LazyInitializationException ka risk (session closed hone ke baad access karo to).
- **Example:** Agar `User` entity add karte aur `User` → `UrlMapping` (OneToMany) relationship hoti, to `LAZY` use karte. Kyunki har User load karne pe uski saari URLs load karna expensive hoga. Jab specifically URLs chahiye tab hi load honi chahiye.

### 5. Click counter service layer mein kyun increment hota hai, controller mein kyun nahi?

- **Separation of concerns** — Controller sirf HTTP request/response handle karta hai, business logic service mein honi chahiye.
- **`@Transactional`** sirf service layer pe lagta hai — click increment aur save ek atomic transaction mein hona zaroori hai. Controller mein ye guarantee nahi milti.
- **Reusability** — Agar future mein koi aur service bhi click track karna chahe to service method reuse ho sakta hai.
- **Testing** — Service layer independently test ho sakti hai bina HTTP layer ke.

### 6. `ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, url).build()` kya return karta hai?

Ye ek HTTP response return karta hai jisme:
- Status code: **302 Found**
- Header: `Location: <original_url>`
- Body: **Empty**

Browser jab ye response receive karta hai, to `Location` header mein diya gaya URL automatically open kar leta hai — ye **HTTP redirect mechanism** hai.

### 7. `@RestControllerAdvice` vs `@ControllerAdvice`?

- `@RestControllerAdvice` = `@ControllerAdvice` + `@ResponseBody`. Automatically response ko JSON serialize karta hai.
- `@ControllerAdvice` traditional MVC apps ke liye hai jahan view (HTML) return hota hai.
- REST APIs banate waqt hamesha `@RestControllerAdvice` use karo.

### 8. Swagger `@ApiResponse` mein `content` attribute kya karta hai?

`content` attribute bataata hai ki response body ka **schema kya hoga** — kaunsa DTO class return hogi. `@Content(schema = @Schema(implementation = UrlResponseDto.class))` se Swagger UI mein response ka structure automatically dikhta hai example values ke saath. Isse API consumers ko pata chalta hai ki response mein kya expect karna hai.

### 9. Agar `ddl-auto=create-drop` production mein set kare to kya hoga?

**DISASTER!** 🔥
- `create-drop` = App start hone pe tables DROP + CREATE hoti hain, app stop hone pe phir DROP hoti hain.
- Production mein saara **data permanently delete** ho jaayega har restart pe.
- Production mein `validate` ya `none` use karna chahiye. Schema changes ke liye **Flyway** ya **Liquibase** (migration tools) use karo.

---

## 🗄 PostgreSQL Table DDL

Hibernate ye table automatically generate karta hai:

```sql
CREATE TABLE url_mapping (
    id              BIGSERIAL       PRIMARY KEY,
    original_url    VARCHAR(2048)   NOT NULL,
    short_code      VARCHAR(8)      NOT NULL,
    click_count     BIGINT          NOT NULL DEFAULT 0,
    created_at      TIMESTAMP       NOT NULL,
    updated_at      TIMESTAMP       NOT NULL,
    CONSTRAINT uk_short_code UNIQUE (short_code)
);

CREATE UNIQUE INDEX idx_short_code ON url_mapping (short_code);
```

### Manually verify karo (optional):

```sql
psql -U postgres -d url_shortener_db -c "\d url_mapping"
```

---

## 📝 Summary

| Component | File | Responsibility |
|---|---|---|
| Entry Point | `Main.java` | Spring Boot application start |
| Entity | `UrlMapping.java` | JPA entity → PostgreSQL table mapping |
| Repository | `UrlMappingRepository.java` | Database operations (CRUD + custom queries) |
| Service | `UrlMappingService.java` | Business logic, short code generation, DTO conversion |
| Controller | `UrlMappingController.java` | 6 REST endpoints, ResponseEntity, Swagger docs |
| DTOs | `UrlRequestDto`, `UrlResponseDto`, `StatsDto`, `ErrorResponseDto` | Input/Output data structures |
| Exceptions | `ShortCodeNotFoundException`, `ShortCodeAlreadyExistsException` | Custom runtime exceptions |
| Exception Handler | `GlobalExceptionHandler.java` | `@RestControllerAdvice` — global error handling |
| Swagger Config | `OpenApiConfig.java` | API metadata for Swagger UI |
| Config | `application.properties` | DB connection, Hibernate, server port |

---

**Built by Shashwat | Capgemini | Spring Boot Case Study**

