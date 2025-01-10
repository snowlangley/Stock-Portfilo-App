import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Create Portfolios table if it doesn't exist
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Portfolios (" +
                            "PortfolioID INT AUTO_INCREMENT PRIMARY KEY, " +
                            "Name VARCHAR(255) NOT NULL, " +
                            "Owner VARCHAR(255) NOT NULL)"
            );

            // Create Transactions table if it doesn't exist
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Transactions (" +
                            "TransactionID INT AUTO_INCREMENT PRIMARY KEY, " +
                            "PortfolioID INT NOT NULL, " +
                            "Ticker VARCHAR(255) NOT NULL, " +
                            "Shares INT NOT NULL, " +
                            "Price DOUBLE NOT NULL, " +
                            "FOREIGN KEY (PortfolioID) REFERENCES Portfolios(PortfolioID))"
            );

            System.out.println("Database tables created successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
