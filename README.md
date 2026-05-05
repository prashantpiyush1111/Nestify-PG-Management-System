# 🏠 Nestify PG Management System

## 📌 Overview
Nestify PG Management System is a Java-based application designed to manage Paying Guest (PG) accommodations efficiently. It simplifies operations between landlords (admins) and tenants by automating room allocation, payments, complaint handling, and role-based access control.

This project follows a layered architecture using DAO and Service design patterns to ensure scalability, maintainability, and separation of concerns.

---

## 🎯 Objectives
- Automate manual PG management processes
- Implement secure authentication and authorization
- Manage tenants, rooms, payments, and complaints efficiently
- Build a scalable backend system

---

## 🚀 Key Features

### 🔐 Authentication & Authorization
- Secure login system
- Role-based access control (ADMIN / TENANT)

### 🏢 Room Management
- Add, update, and delete rooms
- Track room availability
- Assign rooms to tenants

### 👤 Tenant Management
- Register new tenants
- Manage tenant details

### 💰 Payment Management
- Track rent payments
- Maintain payment history

### 🛠️ Complaint Management
- Tenants can raise complaints
- Admin can resolve complaints

---

## 🛠️ Tech Stack

- Java (Core Java)
- Maven
- MySQL
- JDBC
- IntelliJ / Eclipse

---

## 📂 Project Structure

src/main/java/com/nestify/pg

- entity → Data models (User, Room, Tenant, Payment, Complaint)
- dao → Database operations
- service → Business logic
- util → Helper classes
- MainApp.java → Entry point

---

## ⚙️ System Architecture

- Presentation Layer → MainApp (CLI-based)
- Business Layer → Service classes
- Data Access Layer → DAO classes
- Database Layer → MySQL

---

## 🔄 Application Flow

1. User logs in (Admin or Tenant)
2. System verifies credentials
3. Based on role:
   - Admin manages rooms, tenants, payments, complaints
   - Tenant views details, pays rent, raises complaints
4. Data flows through Service → DAO → Database

---

## ▶️ How to Run

1. Clone the repository:
   git clone https://github.com/prashantpiyush1111/nestify-pg-management-system.git 

2. Open in IntelliJ or Eclipse

3. Configure MySQL database:
   - Create database (nestify_db)
   - Update DB credentials in code

4. Build project:
   mvn clean install

5. Run:
   MainApp.java

---

## 🧪 Sample Credentials

Admin:
- Username: admin
- Password: admin123

Tenant:
- Username: tenant1
- Password: 1234

---

## 🔮 Future Enhancements

- Web UI (Spring Boot + React)
- Email/SMS notifications
- Payment gateway integration
- REST APIs
- JWT Authentication

---

## 📌 Key Highlights

- Clean layered architecture
- DAO & Service design patterns
- Role-based access control
- Real-world project use case

---

## 👨‍💻 Author

Prashant Maurya 

---

## ⭐ Contribution

Feel free to fork and contribute.

---

## 📜 License

MIT License