package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() {
    System.out.println("=== ATTEMPTING POSTGRESQL CONNECTION WITH SSL ===");
    System.out.println("Host: " + DatabaseConfig.getDbHost());
    System.out.println("Database: " + DatabaseConfig.getDbName());
    System.out.println("User: " + DatabaseConfig.getDbUser());
    
    try {
        Class.forName("org.postgresql.Driver");
        System.out.println("✅ PostgreSQL Driver loaded successfully");
        
        // Properties untuk SSL
        java.util.Properties props = new java.util.Properties();
        props.setProperty("user", DatabaseConfig.getDbUser());
        props.setProperty("password", DatabaseConfig.getDbPassword());
        props.setProperty("ssl", "true");
        props.setProperty("sslmode", "require");
        
        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://" + DatabaseConfig.getDbHost() + ":" + DatabaseConfig.getDbPort() + "/" + DatabaseConfig.getDbName(),
            props
        );
        
        System.out.println("✅ POSTGRESQL DATABASE WITH SSL CONNECTED SUCCESSFULLY");
        return conn;
        
    } catch (ClassNotFoundException e) {
        System.err.println("❌ PostgreSQL JDBC Driver not found!");
        e.printStackTrace();
        return null;
    } catch (SQLException e) {
        System.err.println("❌ PostgreSQL SSL Connection failed!");
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