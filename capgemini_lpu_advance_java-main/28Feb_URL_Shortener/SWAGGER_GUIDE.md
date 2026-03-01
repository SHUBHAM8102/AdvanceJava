# 📖 SWAGGER UI — COMPLETE STEP-BY-STEP GUIDE (DETAILED)

# URL Shortener Service — Swagger se har endpoint kaise test karna hai

---

# 🔰 PART 1: SWAGGER KYA HAI AUR KYUN USE KARTE HAIN

---

## Swagger kya hai?

Swagger ek **web-based tool** hai jo tumhare Spring Boot application ke **saare REST API endpoints** ko ek web page pe dikhata hai. Isko use karke tum **bina Postman ke**, **bina cURL ke**, seedha **browser mein** apni API test kar sakte ho.

### Swagger ke 3 kaam:

```
1. DOCUMENTATION  → Tumhari API ka pura structure dikhata hai
                    (kaunse endpoints hain, kya bhejana hai, kya milega)

2. TESTING        → "Try it out" button se LIVE API call kar sakte ho
                    (Postman ki zaroorat nahi)

3. SCHEMA VIEWER  → Request/Response body ka JSON structure dikhata hai
                    (kaunse fields hain, kya type hai, kya example hai)
```

### Humare project mein Swagger kaise aaya?

`pom.xml` mein ye dependency add ki hai:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.4</version>
</dependency>
```

Bas ye ek dependency add karte hi Spring Boot **automatically** Swagger UI page generate kar deta hai. Koi alag se HTML page ya configuration likhne ki zaroorat nahi hai.

### Humare project mein Swagger ke liye kya kya kiya?

```
FILE 1: pom.xml                → springdoc dependency add ki
FILE 2: OpenApiConfig.java     → API ka title, version, description set kiya
FILE 3: UrlMappingController   → @Operation, @ApiResponse, @Parameter lagaye
FILE 4: Saare DTOs             → @Schema lagaya har field pe (description + example)
```

---

# 🔰 PART 2: APPLICATION START KARNA

---

## Step 1: PostgreSQL chal raha hai ya nahi — check karo

Windows pe:

```
1. Keyboard pe  Win + R  dabao
2. Type karo:   services.msc
3. Enter dabao
4. List mein dhundho:  postgresql-x64-18
5. Status column mein "Running" likha hona chahiye
6. Agar "Running" nahi hai to right-click → Start
```

Agar PostgreSQL install nahi hai ya service nahi mil rahi — to pehle PostgreSQL install karo.

---

## Step 2: Database exist karta hai ya nahi — check karo

PowerShell kholo aur ye command run karo:

```
$env:PGPASSWORD = "root"; & "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "\l"
```

Output mein `url_shortener_db` dikhna chahiye. Agar nahi dikhe to database banao:

```
$env:PGPASSWORD = "root"; & "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "CREATE DATABASE url_shortener_db;"
```

---

## Step 3: IntelliJ mein Application Start karo

```
1. IntelliJ IDEA kholo
2. Project kholo:  28_Feb_URL_Shortener_Service
3. Left panel mein navigate karo:
   src → main → java → pom → capgemini → Main.java

4. Main.java file kholo
5. Main class ke baayein taraf GREEN ▶ PLAY button dikhega
6. Us play button pe click karo
7. Dropdown aayega → "Run 'Main'" pe click karo

8. Neeche "Run" tab mein console khulega
9. Wait karo jab tak ye message na aaye:

   ┌─────────────────────────────────────────────────────┐
   │  Tomcat started on port 8088                        │
   │  Started Main in 4.567 seconds                      │
   └─────────────────────────────────────────────────────┘

10. Jab ye message aaye — application READY hai!
```

### ⚠ Agar Error aaye to:

```
ERROR 1: "Cannot connect to database"
FIX:     → PostgreSQL service start karo (Step 1 dekho)
         → Database bana lo (Step 2 dekho)
         → application.properties mein password check karo (root hona chahiye)

ERROR 2: "Port 8088 already in use"
FIX:     → Pehle se koi app 8088 pe chal raha hai
         → Task Manager kholo → us process ko band karo
         → Ya application.properties mein port badal do: server.port=9090

ERROR 3: "Compilation error"
FIX:     → pom.xml pe right-click → Maven → Reload Project
         → Wait karo dependencies download hone ka
         → Phir se Run karo
```

---

## Step 4: Swagger UI Browser mein kholo

```
1. Koi bhi browser kholo (Chrome recommended)

2. Address bar mein type karo:

   http://localhost:8088/swagger-ui/index.html

3. Enter dabao

4. Swagger UI page load hoga
```

---

# 🔰 PART 3: SWAGGER UI KA PAGE SAMJHO

---

Jab page khulega to tum ye dekhoge:

```
╔══════════════════════════════════════════════════════════════════╗
║                                                                  ║
║   URL Shortener Service API                          v1.0.0      ║
║                                                                  ║
║   A production-style URL Shortener REST API built with           ║
║   Spring Boot, Spring Data JPA, PostgreSQL, and documented       ║
║   with Swagger/OpenAPI. Submit long URLs and receive short,      ║
║   unique aliases that redirect to the original address.          ║
║                                                                  ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║   URL Shortener  ▼                                               ║
║   ─────────────────────────────────────────────────────────      ║
║                                                                  ║
║   🟢 POST    /api/shorten          Create a short URL            ║
║   🔵 GET     /api/{shortCode}      Redirect to original URL      ║
║   🔵 GET     /api/stats/{shortCode} Get URL statistics           ║
║   🔵 GET     /api/urls             List all URLs (paginated)     ║
║   🔴 DELETE  /api/{shortCode}      Delete a short URL            ║
║   🔵 GET     /api/top              Get top 5 most-clicked URLs   ║
║                                                                  ║
╠══════════════════════════════════════════════════════════════════╣
║                                                                  ║
║   Schemas  ▼                                                     ║
║   ─────────────────────────────────────────────────────────      ║
║   UrlRequestDto                                                  ║
║   UrlResponseDto                                                 ║
║   StatsDto                                                       ║
║   ErrorResponseDto                                               ║
║                                                                  ║
╚══════════════════════════════════════════════════════════════════╝
```

### Page ke 3 Main Sections:

```
SECTION 1 — HEADER (sabse upar)
├── API ka naam:       "URL Shortener Service API"
├── Version:           v1.0.0
├── Description:       API kya karti hai
└── Ye sab OpenApiConfig.java se aata hai

SECTION 2 — ENDPOINTS (beech mein)
├── "URL Shortener" group naam hai (Controller pe @Tag se aata hai)
├── Har endpoint ka ek bar hai
├── Color coding:
│   ├── 🟢 GREEN  = POST   (data create karna)
│   ├── 🔵 BLUE   = GET    (data padhna)
│   └── 🔴 RED    = DELETE  (data delete karna)
├── Har bar pe ek line summary hai (@Operation se aata hai)
└── Click karne pe expand hota hai — details dikhti hain

SECTION 3 — SCHEMAS (sabse neeche)
├── Ye DTOs ka structure dikhata hai
├── Har field ka naam, type, description, example
└── Ye @Schema annotation se aata hai DTOs mein
```

---

# 🔰 PART 4: HAR ENDPOINT STEP-BY-STEP TEST KARO

---

# ──────────────────────────────────────────────
# ENDPOINT 1: POST /api/shorten
# Kaam: Nayi Short URL banata hai
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Tum ek **lambi URL** bhejte ho (jaise YouTube ka link), aur ye tumhe ek **chhoti short code** de deta hai (jaise "aBcD12"). Phir us short code se koi bhi original URL pe pahunch sakta hai.

---

### Step 1.1 — Endpoint dhundho aur expand karo

```
Swagger UI page pe dekho:

   🟢 POST    /api/shorten          Create a short URL

Is GREEN bar pe CLICK karo.

Bar expand hoga aur neeche details dikhenge:
┌─────────────────────────────────────────────────────────────┐
│ POST /api/shorten                                           │
│                                                             │
│ Create a short URL                                          │
│ Accepts a long URL and an optional custom alias, returns    │
│ the shortened URL details.                                  │
│                                                             │
│ Parameters:    (koi nahi — ye POST hai, body mein data hai) │
│                                                             │
│ Request body:  (required)                                   │
│ ┌───────────────────────────────────────────────────────┐   │
│ │ {                                                     │   │
│ │   "originalUrl": "https://www.example.com/very/...",  │   │
│ │   "customAlias": "myAlias1"                           │   │
│ │ }                                                     │   │
│ └───────────────────────────────────────────────────────┘   │
│                                                             │
│ Responses:                                                  │
│   201 — Short URL created successfully                      │
│   400 — Validation failed — invalid input                   │
│   409 — Custom alias already exists                         │
│                                                             │
│                              [ Try it out ]  ← BLUE BUTTON │
└─────────────────────────────────────────────────────────────┘
```

---

### Step 1.2 — "Try it out" button pe click karo

```
Right side mein ek BLUE button hai:  [ Try it out ]

Click karo.

Kya badlega:
├── Request body ka JSON box EDITABLE ho jaayega
│   (pehle sirf read-only tha, ab type kar sakte ho)
├── Ek BLUE "Execute" button appear hoga neeche
└── Ek "Cancel" button bhi aayega (agar cancel karna ho)
```

---

### Step 1.3 — Request Body mein JSON likho

Request body ka box dikhega jisme pehle se example JSON hoga. Usko PURA MITA DO aur naya likho.

#### TEST 1: Simple URL shorten karo (bina custom alias ke)

```
Box mein ye likho:

{
  "originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
}
```

> **Note:** Sirf `originalUrl` diya hai. `customAlias` nahi diya — to server **random short code generate** karega (jaise "kT5mNp" ya "Xr2bQ7z").

#### TEST 2: Custom alias ke saath URL shorten karo

```
Box mein ye likho:

{
  "originalUrl": "https://www.google.com/search?q=spring+boot",
  "customAlias": "google1"
}
```

> **Note:** `customAlias` diya hai — "google1". To short code "google1" banega. Lekin:
> - 6 se 8 characters ke beech hona chahiye
> - Agar "google1" pehle se kisi ne le liya hai to 409 error aayega

---

### Step 1.4 — "Execute" button pe click karo

```
Request body JSON likh diya?

Ab neeche BLUE button dikhega:  [ Execute ]

Click karo.

Swagger tumhare server ko HTTP POST request bhejega.
```

---

### Step 1.5 — Response padho aur samjho

Execute ke baad neeche **2 sections** dikhenge:

```
┌─────────────────────────────────────────────────────────────┐
│  Curl                                                       │
│  ─────                                                      │
│  curl -X 'POST' \                                           │
│    'http://localhost:8088/api/shorten' \                     │
│    -H 'Content-Type: application/json' \                    │
│    -d '{"originalUrl":"https://www.youtube.com/..."}'       │
│                                                             │
│  (ye same request ka curl command hai — ignore karo)        │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│  Server response                                            │
│  ───────────────                                            │
│                                                             │
│  Code: 201                                                  │
│                                                             │
│  Response body:                                             │
│  {                                                          │
│    "shortCode": "kT5mNp",                                   │
│    "shortUrl": "http://localhost:8088/api/kT5mNp",          │
│    "originalUrl": "https://www.youtube.com/watch?v=...",    │
│    "clickCount": 0,                                         │
│    "createdAt": "2026-02-28T21:30:45.123456",               │
│    "updatedAt": "2026-02-28T21:30:45.123456"                │
│  }                                                          │
│                                                             │
│  Response headers:                                          │
│    content-type: application/json                           │
└─────────────────────────────────────────────────────────────┘
```

### Response ke har field ka matlab:

```
"shortCode": "kT5mNp"
    ↳ Ye tumhara generated short code hai
    ↳ Isko yaad rakho — aage ke saare steps mein chahiye

"shortUrl": "http://localhost:8088/api/kT5mNp"
    ↳ Ye FULL short URL hai
    ↳ Ye browser mein paste karoge to redirect hoga

"originalUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
    ↳ Tumne jo URL diya tha woh — confirm ho gaya ki save ho gaya

"clickCount": 0
    ↳ Abhi tak kisi ne short URL use nahi kiya
    ↳ Jab koi redirect hoga to ye badhega

"createdAt": "2026-02-28T21:30:45.123456"
    ↳ Kab bana — ye kabhi change nahi hoga

"updatedAt": "2026-02-28T21:30:45.123456"
    ↳ Kab last update hua — abhi same hai, click hone pe badhega
```

### 🔴 STATUS CODE 201 ka matlab:

```
201 = "Created"
Matlab: Server ne tumhara data successfully save kar liya hai
        Nayi resource ban gayi hai database mein
```

---

### Step 1.6 — 3 aur URLs banao (testing ke liye chahiye aage)

Wapas "Try it out" pe click karo, naya JSON daalo, "Execute" karo. Ye 3 aur banao:

```
URL #2:
{
  "originalUrl": "https://www.github.com"
}

URL #3:
{
  "originalUrl": "https://www.stackoverflow.com/questions"
}

URL #4 (custom alias ke saath):
{
  "originalUrl": "https://www.amazon.in",
  "customAlias": "amazon1"
}
```

> 📝 Har response ka `shortCode` kahi likh lo — aage chahiye hoga!
>
> Maan lo tumhare paas ab ye 4 short codes hain:
> - `kT5mNp` → YouTube
> - `Xr2bQ7z` → GitHub
> - `mN8pLq` → StackOverflow
> - `amazon1` → Amazon (custom alias)

---

# ──────────────────────────────────────────────
# ENDPOINT 2: GET /api/{shortCode}
# Kaam: Short code se original URL pe REDIRECT karta hai
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Jab koi `http://localhost:8088/api/kT5mNp` visit karta hai, to server use automatically **YouTube pe bhej deta hai** (HTTP 302 Redirect). Saath mein **click count +1** ho jaata hai.

---

### Step 2.1 — Endpoint expand karo

```
Swagger UI pe dekho:

   🔵 GET     /api/{shortCode}      Redirect to original URL

Click karo. Expand hoga:

┌─────────────────────────────────────────────────────────────┐
│ GET /api/{shortCode}                                        │
│                                                             │
│ Redirect to original URL                                    │
│ Resolves the short code and redirects (HTTP 302) to the     │
│ original URL. Increments click count.                       │
│                                                             │
│ Parameters:                                                 │
│   shortCode * (required)  string   path                     │
│   Description: The short code to resolve                    │
│   Example: aBcD1234                                         │
│                                                             │
│ Responses:                                                  │
│   302 — Redirect to original URL                            │
│   404 — Short code not found                                │
│                                                             │
│                              [ Try it out ]                 │
└─────────────────────────────────────────────────────────────┘
```

---

### Step 2.2 — "Try it out" pe click karo

```
Click karne pe shortCode field EDITABLE ho jaayega.
Usme pehle se "aBcD1234" example likha hoga — woh mita do.
```

---

### Step 2.3 — Apna short code daalo

```
shortCode field mein likho:

   kT5mNp

(Step 1 mein jo shortCode mila tha woh daalo)
```

---

### Step 2.4 — "Execute" pe click karo

---

### Step 2.5 — Response dekho

```
┌─────────────────────────────────────────────────────────────┐
│  Server response                                            │
│                                                             │
│  Code: 302                                                  │
│                                                             │
│  Response headers:                                          │
│    location: https://www.youtube.com/watch?v=dQw4w9WgXcQ    │
│                                                             │
│  Response body: (empty — koi body nahi hai)                 │
└─────────────────────────────────────────────────────────────┘
```

### Samjho kya hua:

```
STATUS CODE 302 = "Found" (Redirect)

Server ne bola: "Ye resource yahan nahi hai, ye raha asli URL — wahan jao"

"location" header mein original URL hai:
   https://www.youtube.com/watch?v=dQw4w9WgXcQ

Swagger UI mein redirect NAHI hoga — sirf response dikhega.
Lekin BROWSER mein paste karoge to AUTOMATICALLY redirect hoga.

DATABASE mein kya hua:
   clickCount: 0 → 1   (1 badh gaya)
   updatedAt: naya timestamp aa gaya
```

---

### Step 2.6 — 🌐 BROWSER mein REAL redirect test karo

```
1. Chrome ya koi bhi browser kholo

2. Address bar mein type karo:
   http://localhost:8088/api/kT5mNp

3. Enter dabao

4. KYA HOGA:
   ┌─────────────────────────────────────────┐
   │ Browser automatically YouTube pe        │
   │ redirect ho jaayega!                    │
   │                                         │
   │ Address bar mein dikhega:               │
   │ https://www.youtube.com/watch?v=...     │
   │                                         │
   │ YouTube ka page load ho jaayega         │
   └─────────────────────────────────────────┘

5. Ye hi URL shortening ka ASLI kaam hai!
   Chhota URL diya → Bada URL pe pahunch gaye

6. 3-4 BAAR karo ye (har baar click count badhega)
   - Tab kholo → paste karo → Enter
   - Tab kholo → paste karo → Enter
   - Tab kholo → paste karo → Enter
```

> 💡 Ye 3-4 baar isliye karo taaki baad mein Stats aur Top endpoints mein click count dikhe!

---

### Step 2.7 — Galat short code daalo (404 Error Test)

```
Swagger mein wapas jao:
GET /api/{shortCode} → Try it out

shortCode mein likho:   doesNotExist123

Execute karo.

Response:
┌─────────────────────────────────────────────────────────────┐
│  Code: 404                                                  │
│                                                             │
│  {                                                          │
│    "timestamp": "2026-02-28T21:40:00.000000",               │
│    "status": 404,                                           │
│    "message": "Short code 'doesNotExist123' not found",     │
│    "fieldErrors": null                                      │
│  }                                                          │
└─────────────────────────────────────────────────────────────┘

404 = "Not Found"
Matlab: Ye short code database mein hai hi nahi
```

---

# ──────────────────────────────────────────────
# ENDPOINT 3: GET /api/stats/{shortCode}
# Kaam: Short code ki STATISTICS dikhata hai
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Ye bataata hai ki ek short URL ko **kitni baar click** kiya gaya, **kab bana**, aur **kab last baar access** hua.

---

### Step 3.1 — Endpoint expand karo

```
Swagger UI pe:

   🔵 GET     /api/stats/{shortCode}     Get URL statistics

Click karo.
```

---

### Step 3.2 — "Try it out" pe click karo

---

### Step 3.3 — Short code daalo

```
shortCode field mein likho:

   kT5mNp

(Wahi code jo Step 1 mein bana tha aur Step 2 mein redirect kiya tha)
```

---

### Step 3.4 — "Execute" pe click karo

---

### Step 3.5 — Response dekho aur samjho

```
┌─────────────────────────────────────────────────────────────┐
│  Code: 200                                                  │
│                                                             │
│  {                                                          │
│    "shortCode": "kT5mNp",                                   │
│    "originalUrl": "https://www.youtube.com/watch?v=...",    │
│    "clickCount": 4,                                         │
│    "createdAt": "2026-02-28T21:30:45.123456",               │
│    "lastAccessedAt": "2026-02-28T21:42:10.654321"           │
│  }                                                          │
└─────────────────────────────────────────────────────────────┘
```

### Har field ka matlab:

```
"shortCode": "kT5mNp"
    ↳ Kis short code ki stats dekh rahe ho

"originalUrl": "https://www.youtube.com/watch?v=..."
    ↳ Ye short code kis URL pe redirect karta hai

"clickCount": 4
    ↳ TOTAL KITNI BAAR redirect hua
    ↳ Step 2 mein Swagger se 1 baar + Browser se 3 baar = 4

"createdAt": "2026-02-28T21:30:45.123456"
    ↳ Kab banaya tha — ye KABHI change nahi hota

"lastAccessedAt": "2026-02-28T21:42:10.654321"
    ↳ Kab LAST BAAR kisi ne access kiya
    ↳ Har click pe ye update hota hai
    ↳ createdAt se different hai kyunki baad mein click hua
```

### 🔴 STATUS CODE 200 ka matlab:

```
200 = "OK"
Matlab: Request successful — data mil gaya
```

---

### Step 3.6 — Galat short code daalo (404 test)

```
shortCode:   fakeCode99

Response:
   Code: 404
   "message": "Short code 'fakeCode99' not found"
```

---

# ──────────────────────────────────────────────
# ENDPOINT 4: GET /api/urls
# Kaam: Database ki SAARI URLs dikhata hai (PAGE BY PAGE)
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Database mein jitni bhi URL mappings save hain, sab ek **paginated list** mein dikhata hai. Pagination matlab — ek baar mein 10 URLs, phir next page pe agle 10, aur aage.

---

### Step 4.1 — Endpoint expand karo

```
Swagger UI pe:

   🔵 GET     /api/urls     List all URLs (paginated)

Click karo.
```

---

### Step 4.2 — "Try it out" pe click karo

---

### Step 4.3 — Parameters fill karo

```
2 fields dikhenge:

   page:  0          ← Kaunsa page chahiye (0 = pehla page)
   size:  10         ← Ek page mein kitne items

Pehli baar ke liye ye defaults hi theek hain.
```

Agar tumne 4 URLs banaye hain (Step 1 mein) to:

```
page: 0, size: 10    → Saari 4 URLs ek page mein aa jaayengi
page: 0, size: 2     → Pehle 2 URLs dikhenge
page: 1, size: 2     → Baaki 2 URLs dikhenge
page: 2, size: 2     → content: [] (khali — itne pages nahi hain)
```

---

### Step 4.4 — "Execute" pe click karo

---

### Step 4.5 — Response dekho aur samjho

```
┌─────────────────────────────────────────────────────────────┐
│  Code: 200                                                  │
│                                                             │
│  {                                                          │
│    "content": [                                             │
│      {                                                      │
│        "shortCode": "kT5mNp",                               │
│        "shortUrl": "http://localhost:8088/api/kT5mNp",      │
│        "originalUrl": "https://www.youtube.com/...",        │
│        "clickCount": 4,                                     │
│        "createdAt": "2026-02-28T21:30:45.123456",           │
│        "updatedAt": "2026-02-28T21:42:10.654321"            │
│      },                                                     │
│      {                                                      │
│        "shortCode": "Xr2bQ7z",                              │
│        "shortUrl": "http://localhost:8088/api/Xr2bQ7z",     │
│        "originalUrl": "https://www.github.com",             │
│        "clickCount": 0,                                     │
│        "createdAt": "...",                                   │
│        "updatedAt": "..."                                    │
│      },                                                     │
│      {                                                      │
│        "shortCode": "mN8pLq",                               │
│        "shortUrl": "http://localhost:8088/api/mN8pLq",      │
│        "originalUrl": "https://www.stackoverflow.com/...",  │
│        "clickCount": 0,                                     │
│        "createdAt": "...",                                   │
│        "updatedAt": "..."                                    │
│      },                                                     │
│      {                                                      │
│        "shortCode": "amazon1",                              │
│        "shortUrl": "http://localhost:8088/api/amazon1",     │
│        "originalUrl": "https://www.amazon.in",              │
│        "clickCount": 0,                                     │
│        "createdAt": "...",                                   │
│        "updatedAt": "..."                                    │
│      }                                                      │
│    ],                                                       │
│    "pageable": {                                            │
│      "pageNumber": 0,                                       │
│      "pageSize": 10                                         │
│    },                                                       │
│    "totalElements": 4,                                      │
│    "totalPages": 1,                                         │
│    "number": 0,                                             │
│    "size": 10,                                              │
│    "first": true,                                           │
│    "last": true,                                            │
│    "empty": false                                           │
│  }                                                          │
└─────────────────────────────────────────────────────────────┘
```

### Har field ka matlab:

```
"content": [...]
    ↳ ACTUAL DATA — saari URL mappings ki array

"totalElements": 4
    ↳ Database mein TOTAL kitni URLs hain

"totalPages": 1
    ↳ Total kitne pages bane (4 items, page size 10 = 1 page)

"number": 0
    ↳ Current page number (0-based)

"size": 10
    ↳ Page size jo tumne set kiya tha

"first": true
    ↳ Kya ye pehla page hai? Haan

"last": true
    ↳ Kya ye aakhri page hai? Haan (kyunki 1 hi page hai)

"empty": false
    ↳ Kya content khali hai? Nahi (4 items hain)
```

---

### Step 4.6 — Pagination test karo (size = 2)

```
page: 0
size:  2

Execute karo.

Response mein:
   content: 2 URLs dikhenge (pehle 2)
   totalElements: 4
   totalPages: 2       ← Ab 2 pages ban gaye!
   first: true
   last: false         ← Ye aakhri page NAHI hai

Ab badlo:
   page: 1
   size:  2

Execute karo.

Response mein:
   content: 2 URLs dikhenge (baaki 2)
   totalPages: 2
   first: false        ← Ye pehla page NAHI hai
   last: true          ← Ye aakhri page HAI
```

---

# ──────────────────────────────────────────────
# ENDPOINT 5: GET /api/top
# Kaam: TOP 5 sabse zyada clicked URLs dikhata hai
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Database se woh **5 URLs** nikaalta hai jinpe **sabse zyada clicks** hue hain. Click count ke descending order mein (sabse zyada pehle).

---

### Step 5.1 — Endpoint expand karo

```
Swagger UI pe:

   🔵 GET     /api/top     Get top 5 most-clicked URLs

Click karo.
```

---

### Step 5.2 — "Try it out" pe click karo

---

### Step 5.3 — Koi parameter nahi hai — seedha "Execute"

```
Is endpoint mein koi field fill nahi karna hai.
Seedha [ Execute ] pe click karo.
```

---

### Step 5.4 — Response dekho

```
┌─────────────────────────────────────────────────────────────┐
│  Code: 200                                                  │
│                                                             │
│  [                                                          │
│    {                                                        │
│      "shortCode": "kT5mNp",                                 │
│      "shortUrl": "http://localhost:8088/api/kT5mNp",        │
│      "originalUrl": "https://www.youtube.com/...",          │
│      "clickCount": 4,          ← SABSE ZYADA CLICKS        │
│      "createdAt": "...",                                     │
│      "updatedAt": "..."                                      │
│    },                                                       │
│    {                                                        │
│      "shortCode": "Xr2bQ7z",                                │
│      "shortUrl": "http://localhost:8088/api/Xr2bQ7z",       │
│      "originalUrl": "https://www.github.com",               │
│      "clickCount": 0,                                       │
│      "createdAt": "...",                                     │
│      "updatedAt": "..."                                      │
│    },                                                       │
│    ...                                                      │
│  ]                                                          │
└─────────────────────────────────────────────────────────────┘
```

### Samjho:

```
- YouTube wali URL pehle aayi kyunki uska clickCount = 4 (sabse zyada)
- Baaki sab 0 clicks pe hain
- Maximum 5 URLs return hongi (chahe 100 ho database mein)
- Agar database KHALI hai to [] (empty array) aayega — 404 NAHI
```

> 💡 **Ranking badalne ke liye:** Browser mein GitHub wali URL 5-6 baar kholo, phir /api/top check karo — GitHub pehle aa jaayega!

---

# ──────────────────────────────────────────────
# ENDPOINT 6: DELETE /api/{shortCode}
# Kaam: Ek short URL ko PERMANENTLY delete karta hai
# ──────────────────────────────────────────────

## Ye endpoint kya karta hai?

Ek short code ki mapping **database se permanently hata deta hai**. Delete ke baad woh short code kaam nahi karega — redirect nahi hoga, stats nahi dikhenge.

---

### Step 6.1 — Endpoint expand karo

```
Swagger UI pe:

   🔴 DELETE  /api/{shortCode}     Delete a short URL

RED bar pe click karo.
```

---

### Step 6.2 — "Try it out" pe click karo

---

### Step 6.3 — Short code daalo jo delete karna hai

```
shortCode:   amazon1

(Woh custom alias wala URL delete karenge)
```

---

### Step 6.4 — "Execute" pe click karo

---

### Step 6.5 — Response dekho

```
┌─────────────────────────────────────────────────────────────┐
│  Code: 204                                                  │
│                                                             │
│  Response body: (EMPTY — kuch nahi hai)                     │
└─────────────────────────────────────────────────────────────┘

204 = "No Content"
Matlab: Delete successful ho gaya. Koi body return nahi hoti.
```

---

### Step 6.6 — VERIFY karo ki sach mein delete hua

```
METHOD 1 — Stats check karo:
   GET /api/stats/amazon1 → Execute

   Response:
      Code: 404
      "message": "Short code 'amazon1' not found"

   ✅ Confirmed — database se hat gaya!


METHOD 2 — Redirect try karo:
   GET /api/amazon1 → Execute

   Response:
      Code: 404
      "message": "Short code 'amazon1' not found"

   ✅ Ab redirect bhi nahi hoga!


METHOD 3 — List check karo:
   GET /api/urls → Execute

   Response:
      content mein amazon1 NAHI hoga
      totalElements: 3 (pehle 4 tha, ab 3)

   ✅ List se bhi hat gaya!
```

---

### Step 6.7 — Phir se wahi code delete karo (Idempotent test)

```
shortCode:   amazon1     (jo already delete ho chuka hai)

Execute karo.

Response:
   Code: 404
   "message": "Short code 'amazon1' not found"

Ye EXPECTED hai — code exist hi nahi karta ab.
```

---

# 🔰 PART 5: ERROR TESTING — SAARE ERRORS TEST KARO

---

## Error Test 1: Blank URL bhejo

```
ENDPOINT: POST /api/shorten
BODY:
{
  "originalUrl": ""
}

RESPONSE:
   Code: 400
   {
     "timestamp": "...",
     "status": 400,
     "message": "Validation failed",
     "fieldErrors": {
       "originalUrl": "Original URL must not be blank"
     }
   }

KYUN: @NotBlank validation fail hui kyunki URL khali hai
```

---

## Error Test 2: Invalid URL format

```
ENDPOINT: POST /api/shorten
BODY:
{
  "originalUrl": "ye koi url nahi hai bhai"
}

RESPONSE:
   Code: 400
   {
     "status": 400,
     "message": "Validation failed",
     "fieldErrors": {
       "originalUrl": "Must be a valid URL"
     }
   }

KYUN: @URL validation fail hui kyunki ye valid URL format nahi hai
      URL "http://" ya "https://" se start hona chahiye
```

---

## Error Test 3: Custom alias bahut chhota

```
ENDPOINT: POST /api/shorten
BODY:
{
  "originalUrl": "https://www.google.com",
  "customAlias": "ab"
}

RESPONSE:
   Code: 400
   {
     "status": 400,
     "message": "Validation failed",
     "fieldErrors": {
       "customAlias": "Custom alias must be between 6 and 8 characters"
     }
   }

KYUN: @Size(min=6, max=8) validation fail hui
      "ab" sirf 2 characters ka hai — minimum 6 chahiye
```

---

## Error Test 4: Duplicate custom alias

```
STEP A: Pehle ek URL banao custom alias se
   POST /api/shorten
   {"originalUrl": "https://www.google.com", "customAlias": "google1"}
   Response: 201 Created ✅

STEP B: Phir WAHI alias se DOOSRA URL banao
   POST /api/shorten
   {"originalUrl": "https://www.bing.com", "customAlias": "google1"}

RESPONSE:
   Code: 409
   {
     "status": 409,
     "message": "Short code 'google1' already exists"
   }

KYUN: "google1" pehle se database mein hai — duplicate allowed nahi hai
      short_code column pe UNIQUE constraint hai
```

---

## Error Test 5: Non-existent short code access karo

```
ENDPOINT: GET /api/stats/thisDoesNotExist999

RESPONSE:
   Code: 404
   {
     "status": 404,
     "message": "Short code 'thisDoesNotExist999' not found"
   }

KYUN: Database mein ye short code hai hi nahi
```

---

## Error Test 6: Empty body bhejo POST mein

```
ENDPOINT: POST /api/shorten
BODY:
{}

RESPONSE:
   Code: 400
   {
     "status": 400,
     "message": "Validation failed",
     "fieldErrors": {
       "originalUrl": "Original URL must not be blank"
     }
   }

KYUN: originalUrl field diya hi nahi — @NotBlank fail
```

---

# 🔰 PART 6: SCHEMAS SECTION SAMJHO

---

Swagger UI page ke sabse **neeche** "Schemas" section hai. Isme tumhare saare DTOs ka structure dikhta hai.

### Schema 1: UrlRequestDto (click karke expand karo)

```
┌────────────────────────────────────────────────────────┐
│ UrlRequestDto                                          │
│ Request DTO for creating a shortened URL               │
│                                                        │
│ originalUrl*    string                                 │
│                 The original long URL to shorten        │
│                 Example: https://www.example.com/...    │
│                                                        │
│ customAlias     string                                 │
│                 Optional custom alias for the short     │
│                 code (6-8 alphanumeric characters)      │
│                 Example: myAlias1                       │
└────────────────────────────────────────────────────────┘

* = required field
Ye sab @Schema annotation se aata hai UrlRequestDto.java mein
```

### Schema 2: UrlResponseDto

```
┌────────────────────────────────────────────────────────┐
│ UrlResponseDto                                         │
│ Response DTO containing shortened URL details          │
│                                                        │
│ shortCode       string     Example: aBcD1234           │
│ shortUrl        string     Example: http://localhost... │
│ originalUrl     string     Example: https://www.ex...  │
│ clickCount      integer    Example: 42                 │
│ createdAt       string     Example: 2025-06-15T10:32.. │
│ updatedAt       string     Example: 2025-06-15T12:00.. │
└────────────────────────────────────────────────────────┘
```

### Schema 3: StatsDto

```
┌────────────────────────────────────────────────────────┐
│ StatsDto                                               │
│ DTO containing analytics/statistics for a short URL    │
│                                                        │
│ shortCode       string     Example: aBcD1234           │
│ originalUrl     string     Example: https://www.ex...  │
│ clickCount      integer    Example: 42                 │
│ createdAt       string     Example: 2025-06-15T10:32.. │
│ lastAccessedAt  string     Example: 2025-06-15T12:00.. │
└────────────────────────────────────────────────────────┘
```

### Schema 4: ErrorResponseDto

```
┌────────────────────────────────────────────────────────┐
│ ErrorResponseDto                                       │
│ Generic error response DTO for 4xx/5xx responses       │
│                                                        │
│ timestamp       string     Example: 2025-06-15T10:32.. │
│ status          integer    Example: 404                │
│ message         string     Example: Short code 'abc..  │
│ fieldErrors     object     (validation errors map)     │
└────────────────────────────────────────────────────────┘
```

---

# 🔰 PART 7: COMPLETE TESTING CHECKLIST

---

Ye saare tests karo ek ek karke. Har test ke aage ✅ lagao jab ho jaaye:

```
╔════════════════════════════════════════════════════════════════════╗
║  #   ENDPOINT                ACTION                    EXPECTED  ║
╠════════════════════════════════════════════════════════════════════╣
║                                                                   ║
║  --- SUCCESS TESTS ---                                            ║
║                                                                   ║
║  1.  POST /api/shorten       URL without alias          201      ║
║  2.  POST /api/shorten       URL with custom alias      201      ║
║  3.  GET  /api/{code}        Swagger se redirect        302      ║
║  4.  GET  /api/{code}        Browser se redirect        302      ║
║  5.  GET  /api/stats/{code}  Stats check                200      ║
║  6.  GET  /api/urls          List all (default)         200      ║
║  7.  GET  /api/urls          List page=0, size=2        200      ║
║  8.  GET  /api/urls          List page=1, size=2        200      ║
║  9.  GET  /api/top           Top 5 clicked              200      ║
║  10. DELETE /api/{code}      Delete a URL               204      ║
║  11. GET /api/urls           Verify deleted gone         200      ║
║                                                                   ║
║  --- ERROR TESTS ---                                              ║
║                                                                   ║
║  12. POST /api/shorten       Blank URL                  400      ║
║  13. POST /api/shorten       Invalid URL format         400      ║
║  14. POST /api/shorten       Alias too short ("ab")     400      ║
║  15. POST /api/shorten       Duplicate alias            409      ║
║  16. GET  /api/{fake}        Non-existent code          404      ║
║  17. GET  /api/stats/{fake}  Non-existent stats         404      ║
║  18. DELETE /api/{fake}      Delete non-existent        404      ║
║  19. DELETE /api/{code}      Delete already deleted      404      ║
║  20. GET  /api/top           Empty DB (if applicable)   200 []   ║
║                                                                   ║
╚════════════════════════════════════════════════════════════════════╝
```

---

# 🔰 PART 8: ANNOTATIONS — CODE MEIN KYA KIYA SWAGGER KE LIYE

---

### File 1: `pom.xml` — Dependency add ki

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.4</version>
</dependency>
```

Ye ek dependency add karte hi:
- `/swagger-ui/index.html` pe Swagger UI page available ho jaata hai
- `/api-docs` pe OpenAPI JSON specification available hoti hai
- Koi alag HTML ya config likhne ki zaroorat nahi

---

### File 2: `OpenApiConfig.java` — API info set ki

```java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("URL Shortener Service API")    ← Page ka title
                .version("1.0.0")                      ← API version
                .description("A production-style...")   ← Description
                .contact(new Contact()
                    .name("Shashwat")                  ← Author name
                    .email("shashwat@capgemini.com")   ← Contact email
                ));
    }
}
```

---

### File 3: `UrlMappingController.java` — Per-endpoint documentation

```
ANNOTATION              KAHAN                KYA KARTA HAI
─────────────────────────────────────────────────────────────────
@Tag                    Class pe             Endpoints group name
@Operation              Method pe            Summary + description
@ApiResponses           Method pe            Possible status codes
@ApiResponse            @ApiResponses mein   Ek status code detail
@Content                @ApiResponse mein    Response body type
@Schema                 @Content mein        Kaunsa DTO return hoga
@Parameter              Method param pe      Path/Query param detail
@RequestBody (Swagger)  Method param pe      Request body detail
```

---

### File 4: All DTOs — Field-level documentation

```
ANNOTATION      KAHAN               KYA KARTA HAI
─────────────────────────────────────────────────────────────────
@Schema         Class pe            DTO ka description
@Schema         Field pe            Field description + example
                                    "description" → kya hai ye field
                                    "example" → sample value dikhata hai
```

---

# 🔰 PART 9: COMMON PROBLEMS & FIXES

---

```
╔═══════════════════════════════════════════════════════════════════╗
║  PROBLEM                          FIX                            ║
╠═══════════════════════════════════════════════════════════════════╣
║                                                                   ║
║  Swagger page nahi khul raha      → App chal raha hai?            ║
║  (White page / error)             → URL sahi hai?                 ║
║                                   → localhost:8088/swagger-ui/    ║
║                                      index.html                   ║
║                                                                   ║
║  500 Internal Server Error        → PostgreSQL chal raha hai?     ║
║                                   → Database exist karta hai?     ║
║                                   → Password sahi hai?            ║
║                                   → Console mein error padho      ║
║                                                                   ║
║  POST pe 400 aa raha hai          → URL blank to nahi?            ║
║                                   → Valid format? (https://...)   ║
║                                   → Alias 6-8 chars?             ║
║                                   → JSON format sahi?            ║
║                                                                   ║
║  Redirect kaam nahi kar raha      → Short code CASE-SENSITIVE!   ║
║  (browser mein)                   → "kT5mNp" ≠ "kt5mnp"         ║
║                                   → /api/ prefix lagaya?         ║
║                                                                   ║
║  Schemas section khali hai        → DTOs pe @Schema hai?          ║
║                                   → App restart karo              ║
║                                                                   ║
║  Click count nahi badh raha       → GET /api/{code} use karo      ║
║                                   → GET /api/stats/{code} se      ║
║                                      count nahi badhta (read-only)║
║                                                                   ║
╚═══════════════════════════════════════════════════════════════════╝
```

---

**🎉 Ye guide follow karo — har endpoint test ho jaayega, har error case cover ho jaayega, Swagger UI puri samajh aa jaayegi!**

**Built by Shashwat | Capgemini | URL Shortener Service**

