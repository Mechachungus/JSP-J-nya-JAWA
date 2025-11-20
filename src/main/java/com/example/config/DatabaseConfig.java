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
    private static final String DB_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
           + "?sslmode=disable";

    // Getter methods
    public static String getDbUrl() {
        return DB_URL;
    }
    
    public static String getDbUser() {
        return DB_USER;
    }
    
    public static String getDbPassword() {
        return DB_PASSWORD;
    }
    
    public static String getDbHost() { 
        return DB_HOST; 
    }
    public static String getDbPort() { 
        return DB_PORT; 
    }
    public static String getDbName() { 
        return DB_NAME; 
    }
    
    // Driver class name
    public static String getDriverClassName() {
        return DRIVER_CLASS_NAME;
    }
}