# Sports Center Booking System

## Overview

Sports Center Booking System is a console-based Java application designed for managing sports halls, coaches, users, and training bookings.
The application allows administrators or staff to create users and coaches, register sports halls, schedule training sessions, and allow users to join existing bookings.

The project demonstrates the use of **object-oriented programming principles**, **Java collections**, **stream API**, **logging**, and **clean project structure with service layers**.

---

## Features

### Person Management

* Add new **Users**
* Add new **Coaches**
* Store all persons in a collection
* Group persons by type
* Partition persons based on email availability

### Hall Management

* Create sports halls
* Define:

  * Hall name
  * Door number
  * Capacity
  * Supported sport type

### Booking Management

* Create new training bookings
* Assign bookings to coaches
* Allow users to join existing bookings

### Search Functionality

* Search users
* Search halls

### Statistics

* Display **Top 3 coaches by number of bookings**
* Group persons by type
* Partition persons depending on whether they have an email

---

## Application Menu

The application runs through a **console-based menu system**.

Main menu options:

1. Add person
2. Add hall
3. Create booking
4. Join booking
5. Search
6. Statistics
7. Exit

Submenus allow searching and displaying statistics.

---

## Project Structure

```
src/main/java

app
 └ Main.java

entity
 ├ booking
 │   ├ Booking.java
 │   └ BookingService.java
 │
 ├ hall
 │   ├ Hall.java
 │   ├ HallService.java
 │   ├ SportType.java
 │   ├ InvalidHallCapacity.java
 │   ├ Reservable.java
 │   └ Schedulable.java
 │
 ├ person
 │   ├ Person.java
 │   ├ PersonService.java
 │   ├ InvalidOibException.java
 │   │
 │   ├ coach
 │   │   ├ Coach.java
 │   │   └ CoachService.java
 │   │
 │   └ user
 │       ├ User.java
 │       └ UserService.java
 │
 └ search
     └ SearchService.java
```

---

## Technologies Used

* **Java**
* **Java Streams API**
* **Java Collections**
* **SLF4J Logging**
* **Git**
* **IntelliJ IDEA**

---

## Logging

The application uses **SLF4J logging** to track application activity.
Different log levels are used throughout the program:

* `trace` – entering and exiting methods
* `info` – important program actions
* `warn` – unexpected user input
* `error` – exceptions and errors

Logs help track program execution and debug potential issues.

---

## How to Run

1. Clone the repository
2. Open the project in **IntelliJ IDEA**
3. Ensure the project uses a compatible **Java SDK**
4. Run the `Main` class

The program will start in the console and display the main menu.

---

## Author

Bruno Miklin

---

## Purpose of the Project

This project was created as part of a **Java programming course** to demonstrate:

* Object-oriented design
* Layered architecture using services
* Exception handling
* Stream operations and data processing
* Logging and debugging practices
