package com.example.utils;

import com.example.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // Private constructor untuk Singleton pattern
    private DBConnection() {}
    
    // Method untuk mendapatkan koneksi database
    public static Connection getConnection() throws SQLException { // Tambahkan throws SQLException
        // Tidak perlu cek if (connection == null || connection.isClosed())
        
        try {
            // Load MySQL JDBC Driver
            Class.forName(DatabaseConfig.getDriverClassName());
            
            // Create and return NEW connection
            return DriverManager.getConnection(
                DatabaseConfig.getDbUrl(),
                DatabaseConfig.getDbUser(),
                DatabaseConfig.getDbPassword()
            );
        } catch (ClassNotFoundException e) {
            // Sebaiknya tidak hanya mencetak tapi melemparkan RuntimeException
            throw new RuntimeException("PostgreSQL JDBC Driver not found!", e);
        }
    }

}