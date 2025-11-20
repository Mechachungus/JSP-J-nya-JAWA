package com.example.config;

public class DatabaseConfig {
    // Database credentials dari freesqldatabase.com
    private static final String DB_HOST = "db.joepctttmwjxojncyepf.supabase.co";
    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "STEVENFEKFRENBGT";

    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    
    // JDBC URL
    private static final String DB_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    // Getter methods
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