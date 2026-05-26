# BFHL API — Acropolis Campus Hiring

A production-grade REST API built with **Spring Boot 3** for the Acropolis Campus Hiring challenge.
It accepts an array of strings and returns categorized results — odd/even numbers, alphabets, special characters, their sum, and an alternating-caps concat string.

---

## 🚀 Live API

| | URL |
|---|---|
| **Base URL** | `https://bfhl-api-production-f9c6.up.railway.app` |
| **GET** | `https://bfhl-api-production-f9c6.up.railway.app/bfhl` |
| **POST** | `https://bfhl-api-production-f9c6.up.railway.app/bfhl` |

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3.2.5
- Maven
- Lombok
- Jakarta Bean Validation
- JUnit 5 + Mockito
- Deployed on Railway

---

## 📁 Project Structure

```
bfhl-api/
├── src/
│   ├── main/
│   │   ├── java/com/acropolis/bfhl/
│   │   │   ├── BfhlApplication.java
│   │   │   ├── controller/BfhlController.java
│   │   │   ├── dto/
│   │   │   │   ├── BfhlRequest.java
│   │   │   │   └── BfhlResponse.java
│   │   │   ├── service/
│   │   │   │   ├── BfhlService.java
│   │   │   │   └── BfhlServiceImpl.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── InvalidInputException.java
│   │   └── resources/application.properties
│   └── test/
│       └── java/com/acropolis/bfhl/
│           ├── controller/BfhlControllerTest.java
│           └── service/BfhlServiceImplTest.java
├── pom.xml
├── Procfile
└── README.md
```

---

## 📡 API Reference

### GET `/bfhl`

Returns the operation code for this service.

**Live URL:** `https://bfhl-api-production-f9c6.up.railway.app/bfhl`

**Request:**
```bash
curl -X GET https://bfhl-api-production-f9c6.up.railway.app/bfhl
```

**Response — 200 OK:**
```json
{
  "operation_code": 1
}
```

---

### POST `/bfhl`

Processes an array of strings and returns categorized results.

**Live URL:** `https://bfhl-api-production-f9c6.up.railway.app/bfhl`

**Request Body:**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `data` | `string[]` | Yes | Non-null, non-empty array of strings |

**Request:**
```bash
curl -X POST https://bfhl-api-production-f9c6.up.railway.app/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```

**Response — 200 OK:**
```json
{
  "is_success": true,
  "user_id": "kuldeep_kelde_29042006",
  "email": "kuldeepkelde231154@acropolis.in",
  "roll_number": "0827CI231071",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

**Response — 400 Bad Request (validation error):**
```json
{
  "is_success": false,
  "message": "data: data array must not be empty"
}
```

**Response — 400 Bad Request (malformed JSON):**
```json
{
  "is_success": false,
  "message": "Malformed JSON request body"
}
```

---

## 🧠 Field Logic

| Field | Logic |
|-------|-------|
| `odd_numbers` | Purely numeric strings whose integer value is odd |
| `even_numbers` | Purely numeric strings whose integer value is even |
| `alphabets` | Purely alphabetic strings, returned in UPPERCASE |
| `special_characters` | Everything that is not purely numeric or alphabetic |
| `sum` | Sum of all numeric elements as a string (returns `"0"` if none) |
| `concat_string` | Flatten all chars from alphabetic elements → concatenate → reverse → apply alternating caps (index 0 = uppercase) |

**concat_string examples:**

| Input | concat_string |
|-------|--------------|
| `["a", "R"]` | `"Ra"` |
| `["a", "y", "b"]` | `"ByA"` |
| `["A", "ABCD", "DOE"]` | `"EoDdCbAa"` |

---

## ⚙️ Build & Run Locally

**Prerequisites:** Java 17+, Maven 3.8+

**1. Clone the repo:**
```bash
git clone https://github.com/Keldekuldeep/bfhl-api.git
cd bfhl-api
```

**2. Build:**
```bash
mvn clean package
```

**3. Run:**
```bash
java -jar target/bfhl-api-0.0.1-SNAPSHOT.jar
```

Server starts at `http://localhost:8080`.

---

## 🧪 Run Tests

```bash
mvn test
```

**20 tests total — all pass:**
- 11 unit tests (`BfhlServiceImplTest`) — covers all field logic and edge cases
- 9 integration tests (`BfhlControllerTest`) — covers all curl scenarios + error cases

---

## ☁️ Deployment (Railway)

This project is deployed on **Railway** with GitHub auto-deploy. Every push to `main` triggers a new deployment automatically.

**To deploy your own instance:**
1. Fork this repo
2. Go to [railway.app](https://railway.app) → New Project → Deploy from GitHub
3. Select your repo — Railway auto-detects the Java project
4. Add environment variable: `PORT=8080`
5. Go to Settings → Networking → Generate Domain
6. Your public URL is ready

> Before deploying, update the three constants in `BfhlServiceImpl.java`:
> - `USER_ID` → your name + DOB in `name_ddmmyyyy` format
> - `EMAIL` → your college email
> - `ROLL_NUMBER` → your actual roll number

---

## 📦 GitHub Repository

[https://github.com/Keldekuldeep/bfhl-api](https://github.com/Keldekuldeep/bfhl-api)
