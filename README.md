# Stock Portfolio Management System

This is a Java-based application designed to manage a stock portfolio. It provides functionality for creating and managing portfolios, executing stock transactions, and viewing transaction histories.

---

## Features

- **Portfolio Management**: Create, view, and manage multiple stock portfolios.
- **Stock Transactions**: Add, view, and record stock transactions (buy/sell).
- **Database Integration**: Data is stored in a MySQL database for persistence.
- **Interactive Console**: User-friendly, menu-driven command-line interface.

---

## Project Structure

### Source Files

1. **DatabaseConnection.java**  
   Manages the database connection using JDBC and configurations provided in `dbconfig.properties` or `dbconfig.template.properties`.

2. **DatabaseInitializer.java**  
   Initializes the database by creating necessary tables (`Portfolios` and `Transactions`) if they do not exist.

3. **PortfolioManager.java**  
   Core application logic, including menus for portfolio management and stock transactions.

4. **TransactionHistory.java**  
   Handles transaction records and provides methods to add or fetch transaction history for portfolios.

### Configuration Files

- **dbconfig.properties**  
  Contains the database connection URL for the application.

- **dbconfig.template.properties**  
  Template for setting up the database connection URL with placeholders.

---

## Database Schema

### Tables

1. **Portfolios**
   - `PortfolioID` (INT, Primary Key, Auto-Increment)
   - `Name` (VARCHAR, NOT NULL)
   - `Owner` (VARCHAR, NOT NULL)

2. **Transactions**
   - `TransactionID` (INT, Primary Key, Auto-Increment)
   - `PortfolioID` (INT, Foreign Key to `Portfolios`)
   - `Ticker` (VARCHAR, NOT NULL)
   - `Shares` (INT, NOT NULL)
   - `Price` (DOUBLE, NOT NULL)

---

## Prerequisites

- Java 8 or later
- MySQL Database
- JDBC MySQL Driver

---

## Setup and Usage

### 1. Configure Database Connection
- Copy `dbconfig.template.properties` to `dbconfig.properties`.
- Update `dbconfig.properties` with your database details:
  ```properties
  url=jdbc:mysql://<HOST>:<PORT>/<DB_NAME>?useSSL=false&serverTimezone=UTC
  ```

### 2. Compile and Run
- Compile the Java files:
  ```bash
  javac *.java
  ```
- Run the main application:
  ```bash
  java PortfolioManager
  ```

### 3. Application Workflow
1. Launch the application.
2. Enter your name to start.
3. Select options from the main menu:
   - Create a new portfolio.
   - Access existing portfolios.
4. Manage stocks within portfolios:
   - Add transactions.
   - View transaction history.

---

## Future Enhancements

- Add support for more transaction types (e.g., sell, dividends).
- Enhance user interface with a graphical version.
- Implement authentication for better user security.
- Support for multiple databases (e.g., PostgreSQL, SQLite).

---

## Author

**Kyle Hodo**  
Created on: 06/12/2023  
