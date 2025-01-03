# Portfolio Manager Application

**Author:** Kyle Hodo  
**Date Created:** 06/12/2023  

## Overview

This Java application simulates a **stock portfolio management system**. It allows users to perform transactions such as depositing or withdrawing cash, buying or selling stocks, and reviewing their transaction history and portfolio details. The program is designed to mimic the functionality of a brokerage account, offering a simple and interactive way to manage stock investments.

## Features

1. **Deposit Cash:** Add funds to the portfolio account.
2. **Withdraw Cash:** Remove funds from the portfolio account, ensuring sufficient balance.
3. **Buy Stock:** Purchase stocks by specifying the ticker, quantity, and cost basis.
4. **Sell Stock:** Sell stocks by providing similar details as in the buy operation.
5. **Transaction History:** View a detailed log of all transactions, including cash and stock trades.
6. **Portfolio Summary:** Review the current portfolio, including cash balance and stock holdings.

## Components

### 1. **PortfolioManager.java**  
This is the main class that contains:
- **Core Logic:** Handles user interaction and manages the stock portfolio.  
- **Transactions:** Maintains a list of all transactions using `TransactionHistory`.  
- **Utilities:** Includes functions for balance checking, date formatting, and portfolio display.

### 2. **TransactionHistory.java**  
This class manages transaction details:
- **Attributes:** Stores information such as the stock ticker, transaction date, type (buy, sell, deposit, or withdraw), quantity, and cost basis.  
- **Methods:** Includes getters, setters, and the `toPrint()` method for formatted transaction display.

## How It Works

1. **User Interaction:** The user navigates through a menu-driven interface.
2. **Transaction Processing:** Transactions are validated and recorded.
3. **Data Management:** Transactions are stored in a list, enabling portfolio tracking and transaction history display.
4. **Portfolio Updates:** Updates reflect cash balances and stock holdings dynamically.

## Usage Instructions

1. Compile the program:
   ```bash
   javac PortfolioManager.java TransactionHistory.java
   ```
2. Run the program:
   ```bash
   java PortfolioManager
   ```
3. Follow the on-screen menu to interact with the application.

This program is an excellent starting point for understanding the fundamentals of financial transaction systems and stock management.

