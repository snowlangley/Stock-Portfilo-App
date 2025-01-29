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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application; // Base class for JavaFX applications.
import javafx.stage.Stage;             // Represents the main window (or stage) of the application.
import javafx.scene.Scene;             // Holds the content of your stage.
import javafx.scene.layout.VBox;       // A simple vertical layout for organizing UI elements.
import javafx.scene.control.Button;    // Basic UI control for user interaction.


public class PortfolioManager {


  public static void main(String[] args) {
    PortfolioManager portfolioManager = new PortfolioManager();
    Connection connection = DatabaseConnection.getConnection();

    if (connection != null) {
      System.out.println("Database connection established.");
      DatabaseInitializer.initializeDatabase(connection);

      portfolioManager.showMainMenu(connection);
    } else {
      System.err.println("Database connection not established.");
    }
  }

  private void showMainMenu(Connection connection) {
    Scanner scnr = new Scanner(System.in);
    int userSelection = -1;
    String name;

    System.out.print("Please enter your name: ");
    name = scnr.nextLine();



    while (userSelection != 0) {
      try {
        System.out.println("\n" + name + "'s Brokerage Account");
        System.out.println("===============================");
        System.out.println("0 - Exit");
        System.out.println("1 - Create a New Portfolio");
        System.out.println("2 - Access an Existing Portfolio");
        System.out.print("Enter your choice: ");

        userSelection = scnr.nextInt();
        scnr.nextLine(); // Consume newline

        switch (userSelection) {
          case 0 -> System.out.println("Exiting... Thank you!");
          case 1 -> addPortfolio(connection);
          case 2 -> accessExistingPortfolio(connection);
          default -> System.out.println("Invalid choice. Please select 0, 1, or 2.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Please enter a valid number!");
        scnr.nextLine(); // Clear invalid input
      }
    }

    scnr.close();
  }

  public void addPortfolio(Connection connection) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the name of the portfolio: ");
    String portfolioName = scanner.nextLine();

    System.out.print("Enter the name of the owner: ");
    String ownerName = scanner.nextLine();

    String query = "INSERT INTO Portfolios (Name, Owner) VALUES (?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, portfolioName);
      preparedStatement.setString(2, ownerName);
      preparedStatement.executeUpdate();

      ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
      if (generatedKeys.next()) {
        int portfolioId = generatedKeys.getInt(1);
        System.out.println("Portfolio created successfully with ID: " + portfolioId);

        StockApp(connection, portfolioId);
      }

    } catch (Exception e) {
      System.err.println("Error creating portfolio: " + e.getMessage());
    }
  }

  private void accessExistingPortfolio(Connection connection) {
    String query = "SELECT * FROM Portfolios";

    try (Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {

      System.out.println("\nExisting Portfolios:");
      while (resultSet.next()) {
        System.out.printf("ID: %d, Name: %s, Owner: %s\n",
                resultSet.getInt("PortfolioID"),
                resultSet.getString("Name"),
                resultSet.getString("Owner"));
      }

      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter the Portfolio ID to access: ");
      int portfolioId = scanner.nextInt();

      System.out.println("You selected Portfolio ID: " + portfolioId);

      StockApp(connection, portfolioId);

    } catch (Exception e) {
      System.err.println("Error accessing portfolios: " + e.getMessage());
    }
  }

  private void StockApp(Connection connection, int portfolioId) {
    Scanner scanner = new Scanner(System.in);
    TransactionHistory transactionHistory = new TransactionHistory(connection);
    int userSelection = -1;

    while (userSelection != 0) {
      try {
        System.out.println("\nStock Management for Portfolio ID: " + portfolioId);
        System.out.println("======================================");
        System.out.println("0 - Exit to Main Menu");
        System.out.println("1 - Add a Stock Entry");
        System.out.println("2 - View Portfolio Transactions");
        System.out.print("Enter your choice: ");

        userSelection = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (userSelection) {
          case 0 -> System.out.println("Returning to Main Menu...");
          case 1 -> addStockEntry(transactionHistory, portfolioId);
          case 2 -> transactionHistory.printTransactionHistory(portfolioId);
          default -> System.out.println("Invalid choice. Please select 0, 1, or 2.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Please enter a valid number!");
        scanner.nextLine(); // Clear invalid input
      }
    }
  }

  private void addStockEntry(TransactionHistory transactionHistory, int portfolioId) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the stock ticker symbol: ");
    String ticker = scanner.nextLine();

    System.out.print("Enter the number of shares: ");
    int shares = scanner.nextInt();

    System.out.print("Enter the purchase price per share: ");
    double price = scanner.nextDouble();

    transactionHistory.addTransaction(portfolioId, ticker, shares, price);
  }
}
