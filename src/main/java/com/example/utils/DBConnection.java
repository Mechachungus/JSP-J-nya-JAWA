package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    public static Connection getConnection() {
        // Optional: Print logs for debugging (you can remove these in production)
        System.out.println("=== CONNECTING TO DATABASE ===");
        System.out.println("URL: " + DatabaseConfig.getDbUrl());
        
        try {
            // 1. Load the PostgreSQL Driver
            Class.forName("org.postgresql.Driver");
            
            // 2. Get the Full URL (which now includes SSL settings!)
            String url = DatabaseConfig.getDbUrl();
            
            // 3. Set User and Password properties
            Properties props = new Properties();
            props.setProperty("user", DatabaseConfig.getDbUser());
            props.setProperty("password", DatabaseConfig.getDbPassword());
            
            // 4. Connect!
            // Note: We do NOT need to manually add ssl=true here anymore 
            // because getDbUrl() already includes "?sslmode=require..."
            Connection conn = DriverManager.getConnection(url, props);
            
            System.out.println("✅ DATABASE CONNECTED SUCCESSFULLY");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Database Connection failed!");
            System.err.println("Error: " + e.getMessage());
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