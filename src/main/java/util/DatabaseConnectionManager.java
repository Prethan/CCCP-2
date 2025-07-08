package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static DatabaseConnectionManager instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/syos";
    private String username = "root";
    private String password = "";

    private DatabaseConnectionManager() throws SQLException {
        try {
            System.out.println("Connecting to database: " + url + " as user: " + username + " with password: " + password);
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException(ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnectionManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionManager();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionManager();
        }
        return instance;
    }
}
