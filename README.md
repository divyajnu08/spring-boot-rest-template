# Spring Boot OTP Login + JWT Authentication (MVP)

A minimal and clean starter project that provides APIs for OTP-based authentication and token verification.
This version supports phone number + OTP login only, with automatic user creation on first login.
No username/password mechanism is used.

---

## Features

* OTP Authentication (Phone + OTP)
* Auto User Creation on First Login
* JWT Token Generation

## Setup Instructions

### `application.properties`

```
spring.application.name=spring-boot-rest-template

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=yourdbuser
spring.datasource.password=yourdbpassord

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

# JWT
jwt.secret=ad28f7d33baf724a97ac961bc87c230167bd1d9e8a4b689fbd35b9cd1e8a32f1
jwt.expiration-ms=3600000

# Server
server.port=8080
```

---

## Run the Application

```bash
./gradlew bootRun
```

---

# Authentication API (OTP + JWT)

## 1. Verify OTP (Login / Signup)

```
POST /api/auth/verify-otp
```

### Request Body:

```json
{
  "phone": "9876543210",
  "otp": "1234"
}
```

### Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR...",
  "user": {
    "id": 1,
    "phone": "9876543210",
    "name": null
  }
}
```

* If user does NOT exist → auto-created
* If OTP is valid → JWT token returned

---

# Using the JWT Token

Add this header in Postman / CURL / Frontend:

```
Authorization: Bearer <your_token_here>
```

---

# Testing Flow (in Postman)

1. **POST** `/api/auth/verify-otp`  
   → Receive **token**
2. Add Header:

```
Authorization: Bearer <token>
```

3. Call other protected APIs normally

---

# Author

**Divya Srivastava**  
divya.jnu08@gmail.com
