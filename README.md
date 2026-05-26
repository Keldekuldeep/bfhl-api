# BFHL API — Acropolis Campus Hiring

A Spring Boot REST API that processes an array of strings and returns categorized results.

## Build

```bash
mvn clean package
```

## Run Locally

```bash
java -jar target/bfhl-api-0.0.1-SNAPSHOT.jar
```

The server starts on `http://localhost:8080`.

## Sample Request

```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```

## Sample Response

```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

## Run Tests

```bash
mvn test
```

## Deploy to Render

1. Push this repo to GitHub.
2. Go to [render.com](https://render.com) → New → Web Service → connect your repo.
3. Render will auto-detect `render.yaml` and configure the build/start commands.
4. Set environment variable `PORT=8080` if not already set via `render.yaml`.

> Before deploying, replace the placeholder constants in `BfhlServiceImpl.java`:
> - `USER_ID` → your name + DOB in `name_ddmmyyyy` format
> - `EMAIL` → your college email
> - `ROLL_NUMBER` → your actual roll number
