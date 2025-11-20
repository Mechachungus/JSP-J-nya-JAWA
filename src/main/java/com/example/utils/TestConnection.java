package com.example.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("--- Testing Database Connection ---");
        
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ SUCCESS! Connected to Supabase.");
                
                // Print some DB info to prove it's real
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("Database: " + meta.getDatabaseProductName());
                
                conn.close();
            } else {
                System.out.println("❌ Failed: Connection object is null.");
            }
        } catch (Exception e) {
            System.out.println("❌ CRITICAL ERROR:");
            e.printStackTrace();
        }
    }
}