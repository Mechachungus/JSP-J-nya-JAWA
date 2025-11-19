package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;
    
    // Private constructor untuk Singleton pattern
    private DBConnection() {}
    
    // Method untuk mendapatkan koneksi database
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName(DatabaseConfig.getDriverClassName());
                
                // Create connection
                connection = DriverManager.getConnection(
                    DatabaseConfig.getDbUrl(),
                    DatabaseConfig.getDbUser(),
                    DatabaseConfig.getDbPassword()
                );
                
                System.out.println("Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    // Method untuk menutup koneksi
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}