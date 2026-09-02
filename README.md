# 🏠 Nestify — PG Management System

> A full-stack web application for managing paying-guest properties, rooms, tenants, payments, complaints, and PG discovery.

## 📌 Overview

**Nestify** is a full-stack PG (Paying Guest) Management System built with a **Spring Boot backend** and **React frontend** backed by **MySQL**.

The application provides separate experiences for administrators and tenants. Administrators can manage PG operations, while tenants can discover listings, track payments, and raise complaints.

## ✨ Key Features

### 🔐 Authentication & Authorization

- JWT-based authentication
- Role-based access control for `ADMIN` and `TENANT`
- BCrypt password encoding
- Protected backend endpoints

### 🏢 Room Management

- Add, update, and delete rooms
- Track room availability
- Assign rooms to tenants

### 👤 Tenant Management

- Register and manage tenants
- View tenant information
- Connect tenant records with room assignments

### 💰 Payment Management

- Track rent payments
- Mark payments as paid
- Identify pending payments

### 🛠️ Complaint Management

- Tenants can raise complaints
- Admins can manage complaint status
- Workflow: `OPEN → IN_PROGRESS → RESOLVED`

### 🏡 PG Discovery

- Admins can create PG listings with details, pricing, rules, contact information, and photos
- Tenants can browse and search available PGs by city or name

## 🏗️ Architecture

```text
┌──────────────────────┐
│   React + Vite UI    │
│   Tailwind CSS       │
└──────────┬───────────┘
           │ HTTP / REST
           ▼
┌──────────────────────┐
│   Spring Boot API    │
│ Security • Services  │
│ Controllers • JPA    │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│    MySQL Database    │
└──────────────────────┘
```

## 🛠️ Tech Stack

| Layer | Technologies |
|---|---|
| Backend | Java 21, Spring Boot 3.2, Spring Security, Spring Data JPA, Hibernate, Maven |
| Frontend | React 18, Vite, Tailwind CSS, Axios, React Router DOM |
| Database | MySQL |
| Security | JWT, BCrypt, role-based authorization |

## 📂 Project Structure

```text
Nestify-PG-Management-System/
├── src/main/java/com/nestify/pg/
│   ├── config/          # Security and CORS configuration
│   ├── controller/      # REST API endpoints
│   ├── entity/          # JPA entities
│   ├── repository/      # Spring Data repositories
│   ├── service/         # Business logic
│   ├── exception/       # Global exception handling
│   └── util/            # JWT utilities
├── src/main/resources/  # Backend configuration
├── frontend/
│   ├── src/pages/       # React pages
│   ├── src/assets/      # Frontend assets
│   ├── src/api.js       # Axios client
│   └── src/AuthContext.jsx
├── .env.example
└── pom.xml
```

## ⚙️ Local Setup

### Prerequisites

- Java 21
- Maven
- Node.js and npm
- MySQL

### 1. Clone

```bash
git clone https://github.com/prashantpiyush1111/Nestify-PG-Management-System.git
cd Nestify-PG-Management-System
```

### 2. Configure MySQL

Create the database and configure the backend datasource in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nestify_db
spring.datasource.username=root
spring.datasource.password=your_password
```

Keep local credentials and secrets outside version control.

### 3. Run the Backend

Run the Spring Boot application from your IDE or with Maven.

```bash
mvn spring-boot:run
```

The backend runs on port `8080` in the documented local setup.

### 4. Run the Frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173` in your browser.

## 📸 Screenshots

The repository includes application screenshots covering login, tenant dashboard, PG discovery, admin dashboard, room management, and PG listing workflows.

## 📚 Documentation

Detailed project documentation is available in the repository [Wiki](https://github.com/prashantpiyush1111/Nestify-PG-Management-System/wiki), including setup, architecture, features, API reference, roadmap, contributing guidance, and FAQ material.

## 🔒 Security

The application applies JWT-based authentication and role-based authorization, with protected backend resources and BCrypt password encoding.

Do not commit real database passwords, tokens, or other secrets.

## 🗺️ Future Enhancements

- Email/SMS notifications
- Payment gateway integration
- Google Maps integration for PG locations
- Cloud-based image storage
- React Native mobile application

## 👨‍💻 Author

**Prashant Maurya**  
B.Tech CSE | Java Full Stack Developer

GitHub: [@prashantpiyush1111](https://github.com/prashantpiyush1111)

## 📄 License

MIT License
