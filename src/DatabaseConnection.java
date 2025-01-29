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

    // Database file
    static {
        try (FileInputStream input = new FileInputStream("src/dbconfig.properties")) {
            System.out.println("Attempting to load properties file...");
            Properties properties = new Properties();
            properties.load(input);
            URL = properties.getProperty("url");
            System.out.println("Loaded URL: " + URL);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading properties file");
            URL = null;
        }
    }
    // Establish the connection
    public static Connection getConnection() {
        if (URL == null) {
            System.out.println("Database connection not available");
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        Connection connection = null;


        while (connection == null) {
            try {

                // Ask for username
                System.out.print("Enter Database username: ");
                String dbName = scanner.nextLine();

                // Ask for Password
                System.out.print("Enter Database Password: ");
                String dbPass = scanner.nextLine();


                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, dbName, dbPass);
                //System.out.println("Database connection established");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found.");
                //e.printStackTrace();
                return null;
            } catch (SQLException e) {
                System.out.println("Connection to database failed. Incorrect credentials.");
                //e.printStackTrace();
            }
        }
        return connection;
    }
}
