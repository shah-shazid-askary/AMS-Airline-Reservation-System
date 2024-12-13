package airlinemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private Connection connection;

    public Conn() {
        try {
            // Use the correct driver for MySQL 8+
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/airlinemanagementsystem?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "root";
            String password = "S1234567890s";

            // Establish the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found. Ensure mysql-connector-java.jar is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check your credentials or database URL.");
            e.printStackTrace();
        }
    }

    // Getter to provide the connection
    public Connection getConnection() {
        return connection;
    }
}
