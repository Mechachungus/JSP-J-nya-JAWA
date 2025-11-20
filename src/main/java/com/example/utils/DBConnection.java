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
            
                // Create connection
                connection = DriverManager.getConnection(
                    DatabaseConfig.getDbUrl(),
                    DatabaseConfig.getDbUser(),
                    DatabaseConfig.getDbPassword()
                );
                
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            // Print detail error untuk debugging
            System.err.println("URL: " + DatabaseConfig.getDbUrl());
            System.err.println("User: " + DatabaseConfig.getDbUser());
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