/*
** Class Name: IFT-210

** Author: Kyle Hodo

** Date Created: 06/12/2023

** Purpose: This Application is to demonstrate
         stock portfolio that allows you to do
         transactions like buying and selling 
         stock. With the ability to deposit and 
         withdraw like a brokerage account.
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

class PortfolioManager {

  // Store transactions

  private final ArrayList < TransactionHistory > portfolioList = new ArrayList<>();

  public static void main(String[] args) {
    PortfolioManager portfolioManager = new PortfolioManager();
    // Test Connection
    Connection connection = DatabaseConnection.getConnection();
    if (connection != null) {
      System.out.println("Database connection established");
      // Initialize the database schema
      DatabaseInitializer.initializeDatabase(connection);
      portfolioManager.StockApp();
    } else {
      System.out.println("Database connection not established");
    }

  }

  // Add Portfolio
  public void addPortfolio(Connection connection) {
    Scanner scanner = new Scanner(System.in);
    // Propmt for portfolio name and user's name
    System.out.println("Enter the name of the portfolio: ");
    String portfolioName = scanner.nextLine();
    System.out.println("Enter the price of the portfolio: ");
    String ownerName = scanner.nextLine();

    // Save to DB
    String query = "INSERT INTO portfolio VALUES (Name, Owner) VALUES (?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setString(1, portfolioName);
      statement.setString(2, ownerName);
      statement.executeUpdate();
      System.out.println("Portfolio added successfully");

    } catch (SQLException e) {
      System.out.println("Error adding portfolio" + e.getMessage());
    }
  }


  // Stock Portfolio Application

  public void StockApp() {
    Scanner scnr = new Scanner(System.in);

    int userSelection = -1;

    while (userSelection != 0) {
      displayMenu();
      userSelection = scnr.nextInt();
      scnr.nextLine();
      try {

          switch (userSelection) {
              // -----------Exit Program----------------
              case 0 -> System.out.println("Exiting........");


              // -----------Deposit Money-----------
              case 1 -> depositCash();


              // -----------Withdraw Money-----------
              case 2 -> withdrawCash();


              // -----------Buy a stock-----------
              case 3 -> buyStock();


              // -----------Sell a stock-----------
              case 4 -> sellStock();


              // -----------Display Transaction History-----------
              case 5 -> displayTransactionHistory();


              // -----------Display Portfolio-----------
              case 6 -> displayPortfolio();
              default -> System.out.println("Not an option! Please select an option 0 - 6.");
          }

      } catch (InputMismatchException e) {
        System.out.println("Please enter a number!");
        scnr.nextLine();

      }
    }

    scnr.close();
  }

  // User Menu

  private void displayMenu() {
    System.out.println("Kyle Hodo Brokerage Account");
    System.out.println("===============================");
    System.out.println("0 - Exit\n1 - Deposit Cash\n2 - Withdraw Cash\n3 - Buy Stock\n4 - Sell Stock\n5 - Display Transaction History\n6 - Display Portfolio\nEnter option (0 to 6)");

  }

  // Output result for selecting userOption 1 - Deposit Cash

  private void depositCash() {
    Scanner depositCash = new Scanner(System.in);
    try {
      System.out.println("Enter amount: ");
      double amount = depositCash.nextDouble(); // cash amount
      depositCash.nextLine(); 

      // Creating a date variable to output today's date

      Date currentDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      String transDate = dateFormat.format(currentDate);

      TransactionHistory transaction = new TransactionHistory("CASH", transDate, "DEPOSIT", amount, 1.00);
      portfolioList.add(transaction);

      System.out.println("Deposit success!");
      System.out.println();
      //depositCash.close();
    } catch (InputMismatchException e) {
      System.out.println("Please enter an amount in accordance with your account balance.");
      depositCash.nextLine();
    }

  }

  // Output result for selecting userOption 2 - Withdraw Cash

  private void withdrawCash() {
    Scanner withdrawCash = new Scanner(System.in);
    try {
      System.out.println("Enter amount: ");
      double amount = withdrawCash.nextDouble(); // cash amount 
      withdrawCash.nextLine();

      if (sufficientBalance(amount)) {

        // Creating a date variable to output today's date


        Date currDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String transDate = dateFormat.format(currDate);

        TransactionHistory transaction = new TransactionHistory("CASH", transDate, "WITHDRAW", -amount, 1.00);
        portfolioList.add(transaction);

        System.out.println("withdraw success!");

      } else {
        System.out.println("Insufficient balance.");
      }

      System.out.println();
      //withdrawCash.close();
    } catch (InputMismatchException e) {
      System.out.println("Please enter an amount in accordance with your account balance.");
      withdrawCash.nextLine();
    }
  }

  // Output result for selecting userOption 3 - Purchase Stock

  private void buyStock() {
    Scanner buyStock = new Scanner(System.in);
    try {
      System.out.print("Enter stock ticker: ");
      String ticker = buyStock.nextLine(); // Stock Name

      System.out.print("Enter quantity: ");
      double quantity = buyStock.nextDouble(); // Amount of stocks
      buyStock.nextLine();

      System.out.print("Enter cost basis: ");
      double costBasis = buyStock.nextDouble(); // cost per stock
      buyStock.nextLine();

      // logic to determine the amount of being added or removed

      double total = quantity * costBasis;

      if (sufficientBalance(total)) {

        // Creating a date variable to output today's date

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String transDate = dateFormat.format(currentDate);

        TransactionHistory stockTransaction = new TransactionHistory(ticker, transDate, "BUY", quantity, costBasis);
        portfolioList.add(stockTransaction);

        TransactionHistory cashTransaction = new TransactionHistory("CASH", transDate, "WITHDRAW", -total, 1.00);
        portfolioList.add(cashTransaction);

        System.out.println("Stock purchased, thank you for purchase!");

      } else {
        System.out.println("Insufficient balance.");
      }
      System.out.println();
      //buyStock.close();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a number for quantity or cost basis");
      buyStock.nextLine();

    }

  }

  // Output result for selecting userOption 4 - Sell Stock

  private void sellStock() {
    Scanner sellStock = new Scanner(System.in);
    try {
      System.out.print("Enter stock ticker: ");
      String ticker = sellStock.nextLine(); // Stock name

      System.out.print("Enter quantity: ");
      double quantity = sellStock.nextDouble(); // amount of stock
      sellStock.nextLine();

      System.out.print("Enter cost basis: ");
      double costBasis = sellStock.nextDouble(); // cost per stock
      sellStock.nextLine();

      // Logic to determine how much will be added or subtracted

      double total = quantity * costBasis;

      // Creating a date variable to output today's date

      Date currDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
      String transDate = dateFormat.format(currDate);

      TransactionHistory stockTransaction = new TransactionHistory(ticker, transDate, "SELL", quantity, costBasis);
      portfolioList.add(stockTransaction);

      TransactionHistory cashTransaction = new TransactionHistory("CASH", transDate, "DEPOSIT", total, 1.00);
      portfolioList.add(cashTransaction);

      System.out.println("Stock sold, success!");
      System.out.println();
      //sellStock.close();
    } catch (InputMismatchException e) {
      System.out.println("Please enter a number for quantity or cost basis");
      sellStock.nextLine();

    }
  }

  // Output result for selecting userOption 5 - Transaction History

  private void displayTransactionHistory() {
    System.out.println("Transaction History:");
    System.out.println();
    System.out.printf("%-12s %-8s %-15s %-15s %s\n", "Date", "Ticker", "Quantity", "Cost Basis", "Trans Type");
    System.out.println("==================================================================");

    // use toPrint to the userInput
    for (TransactionHistory transaction: portfolioList) {
      transaction.toPrint();

    }

    System.out.println();

  }

  // Output result for selecting userOption 6 - Portfolio

  private void displayPortfolio() {
    System.out.println("Portfolio Information:");
    System.out.println("Portfolio as of: " + getCurrentDate());
    System.out.println("====================================");
    System.out.println("Ticker Quantity");
    System.out.println("=================");

    double cashBalance = 0.0;
    for (TransactionHistory transaction: portfolioList) {
      if (transaction.getTicker().equals("CASH")) {
        cashBalance += transaction.getQty();

      }

    }
    System.out.println("CASH    " + cashBalance);

    /* Used to check if the same ticker is in the list.
    // if so then it will subtract the ticker from 
    // its self to reflect the updated info when 
    // userOption 6 is selected.
    */

    for (TransactionHistory transaction: portfolioList) {
      if (!transaction.getTicker().equals("CASH")) {
        if (transaction.getTransType().equals("BUY")) {
          double quantity = transaction.getQty();
          String ticker = transaction.getTicker();

          for (TransactionHistory sellTransaction: portfolioList) {
            if (sellTransaction.getTicker().equals(ticker) && sellTransaction.getTransType().equals("SELL")) {
              quantity -= sellTransaction.getQty();
            }
          }

          if (quantity > 0) {
            System.out.println(transaction.getTicker() + "   " + quantity);

          }
        }

      }
    }
    System.out.println();

  }

  // method checks if the amount isn't greater than the cashBalance

  private boolean sufficientBalance(double amount) {
    double cashBalance = 0.0;
    for (TransactionHistory transaction: portfolioList) {
      if (transaction.getTicker().equals("CASH")) {
        cashBalance += transaction.getQty();

      }

    }
    return cashBalance >= amount;

  }

  // method for pulling current date and time

  private String getCurrentDate() {
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    return dateFormat.format(currentDate);
  }
}