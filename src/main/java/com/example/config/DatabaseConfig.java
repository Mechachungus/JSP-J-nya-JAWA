package com.example.config;

public class DatabaseConfig {
    
    // --- FALLBACK CREDENTIALS (For Local Testing via IPv4 Pooler) ---
    // 1. Host: Use the pooler address (not db.joepcttt...)
    private static final String DB_HOST = "aws-0-ap-southeast-1.pooler.supabase.com"; 
    // 2. Port: Must be 6543 for the pooler
    private static final String DB_PORT = "6543";
    private static final String DB_NAME = "postgres";
    // 3. User: Must include the project reference
    private static final String DB_USER = "postgres.joepctttmwjxojncyepf";
    private static final String DB_PASSWORD = "STEVENFEKFRENBGT";

    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    
    // Driver class name
    public static String getDriverClassName() {
        return DRIVER_CLASS_NAME;
    }

    // 1. GET URL: Checks Render Environment first, then uses Local Fallback
    public static String getDbUrl() {
        String envUrl = System.getenv("DB_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            return envUrl;
        }
        
        // Construct Local IPv4 Pooler URL
        // Note: We add ?sslmode=require for Supabase
        return "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?sslmode=require";
    }

    // 2. GET USER: Checks Render Environment first, then uses Local Fallback
    public static String getDbUser() {
        String envUser = System.getenv("DB_USER");
        if (envUser != null && !envUser.isEmpty()) {
            return envUser;
        }
        return DB_USER;
    }

    // 3. GET PASSWORD: Checks Render Environment first, then uses Local Fallback
    public static String getDbPassword() {
        String envPass = System.getenv("DB_PASSWORD");
        if (envPass != null && !envPass.isEmpty()) {
            return envPass;
        }
        return DB_PASSWORD;
    }
    
    // Helper getters (Optional, mostly for debugging)
    public static String getDbHost() { return DB_HOST; }
    public static String getDbPort() { return DB_PORT; }
    public static String getDbName() { return DB_NAME; }
}