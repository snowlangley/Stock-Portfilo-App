/*
** Class Name: IFT-210

** Author: Kyle Hodo

** Date Created: 06/12/2023

** Purpose: This grabs all the values entered in PortfolioManger.java
*********** stores them in the values created here in order to print all
*********** all data in a toPrint() method that fits with the corresponding
*********** transHistory method in PortfolioManager.java.
*/



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private final Connection connection;

    public TransactionHistory(Connection connection) {
        this.connection = connection;
    }

    // Original method to add a transaction
    public void addTransaction(String ticker, String transactionDate, String type, int quantity, double costBasis) {
        String query = "INSERT INTO Transactions (Ticker, TransactionDate, Type, Quantity, CostBasis) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, ticker);
            preparedStatement.setString(2, transactionDate);
            preparedStatement.setString(3, type);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setDouble(5, costBasis);
            preparedStatement.executeUpdate();

            System.out.println("Transaction added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
    }

    // New method to add a transaction using portfolioId
    public void addTransaction(int portfolioId, String ticker, int shares, double price) {
        String query = "INSERT INTO Transactions (PortfolioID, Ticker, Shares, Price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, portfolioId);
            preparedStatement.setString(2, ticker);
            preparedStatement.setInt(3, shares);
            preparedStatement.setDouble(4, price);
            preparedStatement.executeUpdate();

            System.out.println("Transaction added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
    }

    // Original method to print a transaction
    public void printTransaction() {
        System.out.printf("%-10s %-15s %-10s %-10s %-10s\n", "Ticker", "Transaction Date", "Type", "Quantity", "Cost Basis");
        System.out.printf("%-10s %-15s %-10s %-10d $%-10.2f\n", "AAPL", "2023-01-10", "Buy", 50, 135.67);
    }

    // New method to fetch transaction history using portfolioId
    public List<String> getTransactionHistory(int portfolioId) {
        String query = "SELECT * FROM Transactions WHERE PortfolioID = ?";
        List<String> transactions = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, portfolioId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String transaction = String.format("ID: %d, Ticker: %s, Shares: %d, Price: %.2f",
                            resultSet.getInt("TransactionID"),
                            resultSet.getString("Ticker"),
                            resultSet.getInt("Shares"),
                            resultSet.getDouble("Price"));
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching transaction history: " + e.getMessage());
        }

        return transactions;
    }

    // New method to print transaction history
    public void printTransactionHistory(int portfolioId) {
        List<String> transactions = getTransactionHistory(portfolioId);
        System.out.println("\nTransaction History for Portfolio ID: " + portfolioId);
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
