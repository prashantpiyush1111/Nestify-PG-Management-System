# 🏠 Nestify PG Management System

## 📌 Overview
Nestify is a full-stack PG (Paying Guest) Management System built with **Spring Boot** backend and **React** frontend. It enables PG owners (admins) to manage rooms, tenants, payments, and complaints, while tenants can browse available PGs, track payments, and raise complaints — all through a modern web interface.

---

## 🎯 Objectives
- Automate PG management operations via a web application
- Implement secure JWT-based authentication and authorization
- Role-based access for Admin and Tenant
- Build a scalable REST API backend with React frontend

---

## 🚀 Key Features

### 🔐 Authentication & Authorization
- JWT-based secure login
- Role-based access control (ADMIN / TENANT)
- BCrypt password encryption

### 🏢 Room Management
- Add, update, delete rooms
- Track room availability
- Assign rooms to tenants

### 👤 Tenant Management
- Register and manage tenants
- View tenant details

### 💰 Payment Management
- Track rent payments
- Mark payments as paid
- Filter pending payments

### 🛠️ Complaint Management
- Tenants can raise complaints
- Admin can update complaint status (OPEN → IN_PROGRESS → RESOLVED)

### 🏡 PG Discovery
- Admin can register their PG listing with photos, price, rules and contact
- Tenants can browse and search available PGs by city or name

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- Spring Data JPA / Hibernate
- MySQL
- Maven

### Frontend
- React 18 (Vite)
- Tailwind CSS
- Axios
- React Router DOM

---

## 📂 Project Structure

nestify-pg-management/
├── src/main/java/com/nestify/pg/
│   ├── config/         → SecurityConfig (JWT filter, role rules), CorsConfig (env-based origins)
│   ├── controller/     → REST API endpoints (Auth, Room, Tenant, Complaint, Payment, PgListing)
│   ├── entity/         → JPA entities (User, Room, Tenant, Complaint, Payment, PgListing, Role)
│   ├── repository/     → Spring Data JPA repositories
│   ├── service/        → Business logic (incl. PgListingService)
│   ├── exception/      → GlobalExceptionHandler (centralized error responses)
│   └── util/           → JwtUtil (token generate/validate)
├── src/main/resources/
│   └── application.properties
├── frontend/
│   ├── src/
│   │   ├── pages/       → React pages
│   │   ├── assets/      → Static assets
│   │   ├── api.js       → Axios client
│   │   └── AuthContext.jsx → JWT auth state
│   └── vite.config.js
├── .env.example         → Environment variable template
├── .gitignore
└── pom.xml

---

## ⚙️ System Architecture

```
React Frontend (port 5173)
        ↓ HTTP/REST
Spring Boot Backend (port 8080)
        ↓ JPA
MySQL Database
```

---

## ▶️ How to Run

### Backend
1. Clone the repository:
```bash
git clone https://github.com/prashantpiyush1111/Nestify-PG-Management-System.git
```
2. Open in Eclipse or IntelliJ
3. Configure MySQL in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nestify_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
4. Run as Spring Boot App (port 8080)

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Open: http://localhost:5173

---

## 🧪 Sample Credentials

| Role   | Username | Password  |
|--------|----------|-----------|
| Admin  | admin    | admin123  |
| Tenant | tenant1  | tenant123 |

---

## 🔮 Future Enhancements
- Email/SMS notifications
- Payment gateway integration
- Google Maps integration for PG location
- Image upload via Cloudinary or S3
- Mobile app using React Native

---

## 👨‍💻 Author
**Prashant Maurya**
B.Tech CSE | IEC College of Engineering and Technology
GitHub: [@prashantpiyush1111](https://github.com/prashantpiyush1111)

---

## 📜 License
MIT License
