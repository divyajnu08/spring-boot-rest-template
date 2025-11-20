# Spring Boot CRUD + JWT Authentication

A simple REST API using Spring Boot + PostgreSQL + JWT Security.

✅ Features

✔ User Registration & Login (JWT)
✔ CRUD API (Users)
✔ PostgreSQL Integration
✔ Gradle Build

📁 Project Structure
src/main/java/com/example/demo/
│
├── config/        → Security & JWT
├── controller/    → REST Controllers
├── dto/           → Request/Response DTOs
├── model/         → Entities
├── repository/    → JpaRepository interfaces
└── service/       → Business Logic

⚙️ Setup Instructions
🔧 Configure application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/demo
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret (Must be 256 bits or more)
jwt.secret=yourVerySecureSecretKeyThatIs256bitsMinimum

spring.main.web-application-type=servlet

▶ Run the application
./gradlew bootRun

🔐 AUTHENTICATION API
➤ Login (Generate JWT Token)

POST /api/auth/login

Request Body
{
"username": "john",
"password": "123456"
}

Response Example
{
"token": "eyJhbGciOiJIUzI1NiIsInR..."
}

📌 Use JWT Token in Request Headers

Add this in Postman / CURL / Frontend:

Authorization: Bearer <your_token_here>

📦 CRUD – User API (Authenticated)
Method	Endpoint	Body (JSON)	Auth Required
POST	/api/users	{ "name": "John", "password": "123456" }	✔ YES
GET	/api/users	-	✔ YES
🧪 TESTING FLOW (Postman)

POST /api/auth/login → Copy token

Add request header:

Authorization: Bearer <token>


Test CRUD APIs normally 🎯

👨‍💻 Author

Your Name Here
💼 Senior Java/Spring Developer
📧 your.email@example.com