package com.example.config;

public class DatabaseConfig {

    // 1. CRITICAL: This method was missing! 
    // Your DBConnection.java needs this to load the PostgreSQL driver.
    public static String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    // 2. Get URL
    public static String getDbUrl() {
        String url = System.getenv("DB_URL");
        if (url == null || url.isEmpty()) {
            // Fallback: Use the hardcoded Supabase URL for local testing
            return "jdbc:postgresql://db.joepctttmwjxojncyepf.supabase.co:5432/postgres?sslmode=require";
        }
        return url;
    }

    // 3. Get User
    public static String getDbUser() {
        String user = System.getenv("DB_USER");
        if (user == null || user.isEmpty()) {
            // Fallback: Use the hardcoded user
            return "postgres"; 
        }
        return user;
    }

    // 4. Get Password
    public static String getDbPassword() {
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isEmpty()) {
            // Fallback: Use the hardcoded password you shared
            return "STEVENFEKFRENBGT"; 
        }
        return password;
    }
}