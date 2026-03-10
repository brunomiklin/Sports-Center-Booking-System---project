# 🏟️ Sports Center Booking System

A Java-based application for managing reservations in a sports center.  
The system allows users to browse available sports facilities and book time slots, while administrators can manage facilities and reservations.

This project was developed as part of a university assignment at **TVZ (Tehničko veleučilište u Zagrebu)**.

---

# 📌 Project Overview

The **Sports Center Booking System** is designed to simplify the process of managing sports facilities and reservations.

The application enables:

- Managing sports facilities
- Creating and viewing reservations
- Preventing overlapping bookings
- Organizing sports center resources efficiently

---

# ⚙️ Technologies Used

- Java
- Maven
- JavaFX
- SQL / H2 Database

---

# 🗂️ Project Structure

```
src
 └── main
     └── java
         └── hr.tvz.sportapp
             ├── controller
             ├── service
             ├── repository
             ├── model
             └── config
```

### Main Components

| Layer | Description |
|------|-------------|
| Controller | Handles HTTP requests and API endpoints |
| Service | Business logic for reservations and facilities |
| Repository | Database communication |
| Model | Entity classes representing system data |

---

# ✨ Features

- 📅 Create and manage reservations
- 🏟️ Manage sports facilities
- 🔍 View available booking slots
- ❌ Prevent duplicate bookings
- 📊 Organized facility management

---

# 🚀 Getting Started

## 1️⃣ Clone the repository

```bash
git clone https://github.com/brunomiklin/Sports-Center-Booking-System---project.git
```

## 2️⃣ Navigate to the project folder

```bash
cd Sports-Center-Booking-System---project
```

## 3️⃣ Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or run the main class from your IDE.

---

# 📡 Example API Endpoints

| Method | Endpoint | Description |
|------|------|-------------|
| GET | /facilities | Get all sports facilities |
| GET | /bookings | Get all bookings |
| POST | /bookings | Create a new booking |
| DELETE | /bookings/{id} | Cancel booking |

---

# 🧠 System Logic

The system ensures that:

- Two users cannot book the same facility at the same time
- Reservations are stored in the database
- Facilities can be managed by the system

This prevents scheduling conflicts and ensures efficient facility usage.

---

# 📚 Educational Purpose

This project was created for learning purposes and demonstrates:

- Backend development with Spring Boot
- REST API design
- Layered architecture
- Database integration

---

# 👤 Author

**Bruno Miklin**

GitHub:  
https://github.com/brunomiklin

---

# 📄 License

This project is intended for **educational use**.
