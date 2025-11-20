package com.example.config;

public class DatabaseConfig {
    
    // 1. CRITICAL: Change this to the PostgreSQL driver
    // If this still says "com.mysql.cj.jdbc.Driver", it will fail!
    public static String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    // 2. Get URL from Render Environment Variable
    public static String getDbUrl() {
        String url = System.getenv("DB_URL");
        if (url == null || url.isEmpty()) {
            // Fallback for local testing if needed
            return "jdbc:postgresql://localhost:5432/your_local_db";
        }
        return url;
    }

    // 3. Get User from Render Environment Variable
    public static String getDbUser() {
        String user = System.getenv("DB_USER");
        if (user == null || user.isEmpty()) {
            return "postgres"; // Default local user
        }
        return user;
    }

    // 4. Get Password from Render Environment Variable
    public static String getDbPassword() {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            return "your_local_password"; 
        }
        return password;
    }
}