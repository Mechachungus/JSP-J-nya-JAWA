package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() {
        System.out.println("=== ATTEMPTING POSTGRESQL CONNECTION ===");
        System.out.println("URL: " + DatabaseConfig.getDbUrl());
        
        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL Driver loaded successfully");
            
            Connection conn = DriverManager.getConnection(
                DatabaseConfig.getDbUrl(),
                DatabaseConfig.getDbUser(),
                DatabaseConfig.getDbPassword()
            );
            
            System.out.println("✅ POSTGRESQL DATABASE CONNECTED SUCCESSFULLY");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ PostgreSQL Connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            e.printStackTrace();
            return null;
        }
    }
    
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}