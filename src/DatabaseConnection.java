import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.io.IOException;
import java.util.Properties;


public class DatabaseConnection {
    // Define connection parameters
    private static String URL;

    static {
        try (FileInputStream input = new FileInputStream("dbconfig.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("url");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading properties file");
            URL = null;
        }
    }

    public static Connection getConnection() {
        if (URL == null) {
            System.out.println("Database connection not available");
            return null;
        }

        try {
            Scanner scanner = new Scanner(System.in);

            // Ask for username
            System.out.print("Enter Database Name: ");
            String dbName = scanner.nextLine();

            // Ask for Password
            System.out.print("Enter Database Password: ");
            String dbPass = scanner.nextLine();


            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            return DriverManager.getConnection(URL, dbName, dbPass);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Connection to database failed.");
            e.printStackTrace();
            return null;
        }
    }
}
