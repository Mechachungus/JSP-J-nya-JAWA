package com.example.dao;

import com.example.models.User;
import com.example.utils.DBConnection;
import java.sql.*;

public class UserDAO {
    
    // Fungsi untuk REGISTER - Insert user baru (Account + Customer)
    public boolean registerUser(User user, String password) {
        Connection conn = null;
        PreparedStatement stmtAccount = null;
        PreparedStatement stmtCustomer = null;
        boolean success = false; // Flag untuk status keberhasilan

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Mulai transaksi

            // 1. Generate ID untuk Account dan Customer
            String accountID = generateAccountID(conn);
            String customerID = generateCustomerID(conn);

            // 2. Insert ke table ACCOUNT
            String sqlAccount = "INSERT INTO account (ID, Username, Password, Type) VALUES (?, ?, ?, ?)";
            stmtAccount = conn.prepareStatement(sqlAccount);
            stmtAccount.setString(1, accountID);
            stmtAccount.setString(2, user.getUsername());
            stmtAccount.setString(3, password);
            stmtAccount.setString(4, "Customer"); // Default type Customer

            int rowsAccount = stmtAccount.executeUpdate();

            // 3. Insert ke table CUSTOMERS
            String sqlCustomer = "INSERT INTO customers (ID, Name, Gender, Birth_Date, Phone_Number, Account_ID, Membership_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmtCustomer = conn.prepareStatement(sqlCustomer);
            stmtCustomer.setString(1, customerID);
            stmtCustomer.setString(2, user.getFullName());
            stmtCustomer.setString(3, user.getGender());
            stmtCustomer.setString(4, user.getBirthDate());
            stmtCustomer.setString(5, user.getPhoneNumber());
            stmtCustomer.setString(6, accountID);
            stmtCustomer.setString(7, "MEM000001"); // Default membershipID: Basic

            int rowsCustomer = stmtCustomer.executeUpdate();

            if (rowsAccount > 0 && rowsCustomer > 0) {
                conn.commit(); // Commit transaksi jika kedua insert berhasil
                success = true;
            } else {
                conn.rollback(); // Rollback jika ada yang gagal
            }
        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Pastikan rollback jika terjadi exception
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
            }
        } finally {
            // Close resources
            try {
                if (stmtCustomer != null) stmtCustomer.close();
                if (stmtAccount != null) stmtAccount.close();
                if (conn != null) conn.close(); // Gunakan try-with-resources di DBConnection jika memungkinkan
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    // Fungsi untuk LOGIN - Ambil data user berdasarkan username dan password
    public User loginUser(String username, String password) {
        System.out.println("=== DEBUG LOGIN ===");
        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + password + "'");
        System.out.println("Password length: " + password.length());
        
        String sql = "SELECT a.ID as accountID, a.Username, a.Type, " +
                    "c.ID as customerID, c.Name, c.Gender, c.Birth_Date, c.Phone_Number, c.Membership_ID " +
                    "FROM account a " +
                    "LEFT JOIN customers c ON a.ID = c.Account_ID " +
                    "WHERE a.Username = ? AND a.Password = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            
            if (conn == null) {
                System.err.println("DATABASE CONNECTION IS NULL!");
                return null;
            }
            
            System.out.println("Database connected successfully");
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            System.out.println("Executing query: " + sql);
            
            rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("LOGIN SUCCESS - User found in database");
                User user = new User(
                    rs.getString("customerID"),
                    rs.getString("accountID"), 
                    rs.getString("Username"),
                    rs.getString("Name"),
                    rs.getString("Phone_Number"),
                    rs.getString("Gender"),
                    rs.getString("Birth_Date"),
                    rs.getString("Type"),
                    rs.getString("Membership_ID")
                );
                System.out.println("User details: " + user.getUsername() + ", " + user.getFullName());
                return user;
            } else {
                System.out.println("LOGIN FAILED - No user found with these credentials");
                // Debug: cek apakah username ada
                checkUsernameExists(username, conn);
            }
            return null;

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { }
            try { if (stmt != null) stmt.close(); } catch (Exception e) { }
            try { if (conn != null) conn.close(); } catch (Exception e) { }
        }
    }

// Method helper untuk debug
private void checkUsernameExists(String username, Connection conn) {
    try {
        String checkSql = "SELECT Username FROM account WHERE Username = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, username);
        ResultSet checkRs = checkStmt.executeQuery();
        
        if (checkRs.next()) {
            System.out.println("DEBUG: Username '" + username + "' EXISTS in database");
            System.out.println("Stored username: '" + checkRs.getString("Username") + "'");
        } else {
            System.out.println("DEBUG: Username '" + username + "' NOT FOUND in database");
        }
        checkRs.close();
        checkStmt.close();
    } catch (SQLException e) {
        System.err.println("Debug check failed: " + e.getMessage());
    }
}
    
    // Fungsi untuk mendapatkan ID Account tertinggi dan men-generate ID baru (ACCxxxxxx)
    private String generateAccountID(Connection conn) throws SQLException {
        String newID = "ACC000001"; // Default ID jika tabel kosong
        String sql = "SELECT MAX(ID) AS max_id FROM account";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String maxID = rs.getString("max_id");
                if (maxID != null && maxID.startsWith("ACC")) {
                    int num = Integer.parseInt(maxID.substring(3)) + 1;
                    newID = String.format("ACC%06d", num);
                }
            }
        }
        return newID;
    }

    // Fungsi untuk mendapatkan ID Customer tertinggi dan men-generate ID baru (CUSxxxxxx)
    private String generateCustomerID(Connection conn) throws SQLException {
        String newID = "CUS000001"; // Default ID jika tabel kosong
        String sql = "SELECT MAX(ID) AS max_id FROM customers";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String maxID = rs.getString("max_id");
                if (maxID != null && maxID.startsWith("CUS")) {
                    int num = Integer.parseInt(maxID.substring(3)) + 1;
                    newID = String.format("CUS%06d", num);
                }
            }
        }
        return newID;
    }
    
    // Fungsi untuk cek apakah username sudah ada
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM account WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Fungsi untuk UPDATE profile user (Name, Gender, Birth_Date, Phone_Number)
    public boolean updateUserProfile(User user) {
        // Menggunakan subquery untuk update customers berdasarkan username di account
         String updateSql = "UPDATE customers c " +
                             "SET Name = ?, Gender = ?, Birth_Date = ?, Phone_Number = ? " +
                             "WHERE c.Account_ID = (SELECT ID FROM account WHERE Username = ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSql)) {
            
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getGender());
            stmt.setString(3, user.getBirthDate());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getUsername());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Fungsi untuk UPDATE password
    public boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE account SET Password = ? WHERE Username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}