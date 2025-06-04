package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static final String URL = "jdbc:postgresql://localhost:5431/vehicle-db";
    private static final String USER = "manager";
    private static final String PASSWORD = "manager";

    private ConnectionProvider() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
