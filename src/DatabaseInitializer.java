import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        String schema = """
        CREATE TABLE IF NOT EXISTS Portfolios (
            PortfolioID INT AUTO_INCREMENT PRIMARY KEY,
            Name VARCHAR(100) NOT NULL,
            Owner VARCHAR(100),
            CreatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

        CREATE TABLE IF NOT EXISTS Transactions (
            TransactionID INT AUTO_INCREMENT PRIMARY KEY,
            PortfolioID INT,
            Type ENUM('BUY', 'SELL') NOT NULL,
            Amount DECIMAL(10, 2) NOT NULL,
            Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (PortfolioID) REFERENCES Portfolios(PortfolioID)
        );
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(schema);
            System.out.println("Database initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

