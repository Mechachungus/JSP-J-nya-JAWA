package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    
    public static Connection getConnection() {
        System.out.println("============================================");
        System.out.println("🔍 DEBUGGING CONNECTION START");
        
        // 1. Check Raw Environment Variable
        String rawEnv = System.getenv("DB_URL");
        System.out.println("👉 System.getenv('DB_URL'): " + (rawEnv == null ? "NULL" : rawEnv));
        
        // 2. Check what DatabaseConfig returns
        String configUrl = DatabaseConfig.getDbUrl();
        System.out.println("👉 DatabaseConfig.getDbUrl(): " + configUrl);
        
        try {
            Class.forName("org.postgresql.Driver");
            
            Properties props = new Properties();
            props.setProperty("user", DatabaseConfig.getDbUser());
            props.setProperty("password", DatabaseConfig.getDbPassword());
            
            // 3. Print user (masked password)
            System.out.println("👉 DB User: " + props.getProperty("user"));
            
            System.out.println("🔄 Attempting DriverManager.getConnection...");
            Connection conn = DriverManager.getConnection(configUrl, props);
            
            System.out.println("✅ SUCCESS! Connection established.");
            System.out.println("============================================");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ DRIVER ERROR: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.err.println("❌ SQL ERROR: " + e.getMessage());
            System.err.println("❌ SQL STATE: " + e.getSQLState());
            return null;
        } catch (Exception e) {
             System.err.println("❌ UNKNOWN ERROR: " + e.getMessage());
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