
# Hotel Paradise - Smart Booking System

A complete Java console application demonstrating **core OOP principles** and **custom exception handling** through a real-world hotel management scenario.

## Features
- Room management (Standard, Deluxe, Suite)
- Guest registration with validation
- Room booking with conflict detection
- Cancellation with status tracking
- Payment processing
- Revenue reporting
- Interactive menu-driven interface

## OOP Concepts Used
- **Abstraction**: Abstract `Room` class, `Bookable` interface
- **Encapsulation**: Private fields with getters/setters, validation in setters
- **Inheritance**: `StandardRoom`, `DeluxeRoom`, `SuiteRoom` extend `Room`
- **Polymorphism**: `calculateBill()` method overridden per room type

## Exception Handling
- **Checked**: `RoomAlreadyBookedException`, `RoomNotFoundException`, `GuestNotFoundException`, `InvalidCheckoutDateException`
- **Unchecked**: `InvalidPaymentException` (extends `RuntimeException`)
- Proper `try-catch` blocks and `throws` declarations

## How to Run

### Windows (Command Prompt)
```cmd
mkdir out
javac -d out src\exception\*.java src\model\*.java src\service\*.java src\Main.java
java -cp out Main
```

### Linux/Mac (Terminal)
```bash
mkdir out
javac -d out src/exception/*.java src/model/*.java src/service/*.java src/Main.java
java -cp out Main
```

## Sample Menu


1. Register Guest
2. View All Rooms
3. View Available Rooms
4. Book Room
5. Cancel Booking
6. Process Payment
7. View All Bookings
8. Revenue Report
9. Exit


## Technologies
- Java 8+ (uses `java.time` API)
- No external libraries
- Pure OOP implementation

---

**Educational project** built from scratch to demonstrate Java fundamentals for academic submissions.
