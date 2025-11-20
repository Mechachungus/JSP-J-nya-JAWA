package com.example.config;

public class DatabaseConfig {
    
    // --- FALLBACK CREDENTIALS (For Local Testing via IPv4 Pooler) ---
    private static final String DB_HOST = "aws-0-ap-southeast-1.pooler.supabase.com"; 
    private static final String DB_PORT = "6543";
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres.joepctttmwjxojncyepf";
    private static final String DB_PASSWORD = "STEVENFEKFRENBGT";

    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    
    public static String getDriverClassName() {
        return DRIVER_CLASS_NAME;
    }

    // 1. GET URL
    public static String getDbUrl() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        
        // Construct Local IPv4 Pooler URL with Non-Validating SSL Factory
        // This fixes the "root.crt not found" error on Windows
        return "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
             + "?sslmode=require"
             + "&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    }

    // 2. GET USER
    public static String getDbUser() {
        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isEmpty()) {
            return envUser;
        }
        return DB_USER;
    }

    // 3. GET PASSWORD
    public static String getDbPassword() {
        String envPass = System.getenv("DB_PASSWORD");
        if (envPass != null && !envPass.isEmpty()) {
            return envPass;
        }
        return DB_PASSWORD;
    }
    
    // Helper getters
    public static String getDbHost() { return DB_HOST; }
    public static String getDbPort() { return DB_PORT; }
    public static String getDbName() { return DB_NAME; }
}