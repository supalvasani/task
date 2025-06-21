# 📅 To-Do List API

A simple RESTful To-Do List API built with Spring Boot and PostgreSQL. This project is a part of the **Keploy API Fellowship** and fulfills the requirement to build a **custom API server** with database integration and API documentation.

---

## 🚀 Overview

This server allows users to:

* Register and log in 
* Create, read, update, and delete their personal to-do tasks
* Mark tasks as complete/incomplete
* Fetch tasks based on status (complete/incomplete)

---

## ⚖️ Tech Stack

* **Java 21**
* **Spring Boot**
* **Spring JDBC template**
* **PostgreSQL**
* **Swagger (OpenAPI)**
* **Lombok**

---

## 📊 APIs Created

### 🔐 Auth APIs

| Method | Endpoint             | Description         |
| ------ | -------------------- | ------------------- |
| POST   | `/auth/register`     | Register new user   |
| POST   | `/auth/login`        | User login          |
| GET    | `/auth/getuser/{id}` | Get user by ID      |
| PUT    | `/auth/update/{id}`  | Update user details |

### 📅 Task APIs

| Method | Endpoint                       | Description                |
| ------ | ------------------------------ | -------------------------- |
| POST   | `/task`                        | Create a new task          |
| GET    | `/users/{id}/tasks`            | Get all tasks for a user   |
| GET    | `/users/{id}/tasks/completed`  | Get completed tasks        |
| GET    | `/users/{id}/tasks/incomplete` | Get incomplete tasks       |
| PUT    | `/tasks/{id}`                  | Update task content/status |
| PUT    | `/tasks/{id}/complete`         | Mark task as complete      |
| DELETE | `/tasks/{id}`                  | Delete a task by ID        |

---

## 📚 Database Integration

* **PostgreSQL** used as the database
* Spring Boot configured to connect via `application.properties`
* `Task` and `User` models mapped using JPA annotations
* Custom repository interfaces (e.g., `TaskRepo`, `UserRepo`) for DB operations

> NOTE: `application.properties` is excluded from the public repo. Use `application-template.properties` as a reference.

---

## 🔧 How to Run

### 1. Clone the Repo

```bash
git clone https://github.com/supalvasani/task.git
cd task
```

### 2. Configure the Database

1. Create a PostgreSQL database (e.g., `tododb`)
2. Copy template file:

```bash
cp src/main/resources/application-template.properties src/main/resources/application.properties
```

3. Update `application.properties` with your DB credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tododb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Server runs on: `http://localhost:8080`

---

## 🔮 Swagger / API Documentation

Swagger UI is enabled for testing:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔍 Sample cURL Requests

### Register User

```bash
curl -X POST http://localhost:8080/auth/register \
 -H "Content-Type: application/json" \
 -d '{"username": "john", "email": "john@example.com", "password": "1234"}'
```

### Create Task

```bash
curl -X POST http://localhost:8080/task \
 -H "Content-Type: application/json" \
 -d '{"userId": "1", "title": "Complete Assignment"}'
```

---

## 🚪 Frontend (Optional)

Not implemented yet, but API is ready for frontend integration (React, Vite, etc.)

---

## 💼 Challenges Faced

* Handling missing user/task exceptions (solved using custom exception handlers)
* Structuring DTOs for response/request cleanliness
* Securing credentials (used `.gitignore` and removed tracked `application.properties`)

---

## 👥 Author

Made with by [Supal Vasani](https://github.com/supalvasani)

> Feel free to fork, clone, or submit a PR!

---
