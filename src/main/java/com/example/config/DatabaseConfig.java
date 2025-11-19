package com.example.config;

public class DatabaseConfig {
    // Database credentials dari freesqldatabase.com
    private static final String DB_HOST = "sql12.freesqldatabase.com";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "sql12808594";
    private static final String DB_USER = "sql12808594";
    private static final String DB_PASSWORD = "FiD2PxbClw";
    
    // JDBC URL
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
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
    
    // Driver class name
    public static String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}