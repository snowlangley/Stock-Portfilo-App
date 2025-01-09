import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase(Connection connection) {
        String createPortfoliosTable = """
            CREATE TABLE IF NOT EXISTS Portfolios (
                PortfolioID INT AUTO_INCREMENT PRIMARY KEY,
                Name VARCHAR(100) NOT NULL,
                Owner VARCHAR(100),
                CreatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        String createTransactionsTable = """
            CREATE TABLE IF NOT EXISTS Transactions (
                TransactionID INT AUTO_INCREMENT PRIMARY KEY,
                PortfolioID INT,
                Type ENUM('BUY', 'SELL') NOT NULL,
                Amount DECIMAL(10, 2) NOT NULL,
                Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (PortfolioID) REFERENCES Portfolios(PortfolioID)
            );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createPortfoliosTable);
            System.out.println("Portfolios table created successfully.");

            statement.executeUpdate(createTransactionsTable);
            System.out.println("Transactions table created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
