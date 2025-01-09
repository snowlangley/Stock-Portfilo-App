# Portfolio Manager Application

**Author:** Kyle Hodo  
**Date Created:** June 12, 2023  

## Overview

The **Portfolio Manager Application** is a Java-based system designed to simulate a stock portfolio management environment. Users can perform various financial operations, such as depositing or withdrawing cash, buying or selling stocks, and viewing transaction histories. This application mimics brokerage account functionalities, providing an intuitive interface to manage stock investments.

---

## Features

- **Deposit Cash**: Add funds to the portfolio account.
- **Withdraw Cash**: Remove funds from the portfolio, ensuring sufficient balance.
- **Buy Stock**: Purchase stocks by specifying details like ticker, quantity, and cost basis.
- **Sell Stock**: Sell stocks with similar detail inputs.
- **Transaction History**: Review a detailed log of all past transactions.
- **Portfolio Summary**: View the current portfolio, including cash balance and stock holdings.

---

## Components

### PortfolioManager.java
- **Core Logic**: Manages user interactions and portfolio operations.
- **Transactions**: Uses `TransactionHistory` to log all transactions.
- **Utilities**: Provides methods for balance checks, portfolio display, and date formatting.

### TransactionHistory.java
- **Attributes**: Stores transaction details such as ticker, type (buy, sell, deposit, withdraw), date, quantity, and cost basis.
- **Methods**: Includes `toPrint()` for formatted transaction output and standard getters/setters.

---

## How It Works

1. **Interactive Menu**: Users navigate an intuitive menu-driven interface.
2. **Transaction Processing**: Validates and records user transactions dynamically.
3. **Data Management**: Stores transactions for easy retrieval and review.
4. **Real-Time Updates**: Reflects portfolio changes, including cash balance and stock holdings.

---

## Setting Up the Database Connection

### Prerequisites
1. Ensure a MySQL server is installed and running.
db.url=jdbc:mysql://<HOST>:<PORT>/<DATABASE_NAME>?useSSL=false&serverTimezone=UTC
3. Create a database named `PortfolioDB` and configure the schema.

### Configuration
1. Add a file named `dbconfig.properties` to the `src` folder with the following format:

2. Run the program to connect to the database.

### Database Initialization
The program initializes the database schema automatically on the first run.

---

## Usage Instructions

1. **Compile the Program**:
```bash
javac -d out -sourcepath src src/PortfolioManager.java src/TransactionHistory.java src/DatabaseConnection.java src/DatabaseInitializer.java
