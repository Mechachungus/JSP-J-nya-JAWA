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
        
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
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
            stmtCustomer.setString(7, "M1"); // Default membership Basic
            
            int rowsCustomer = stmtCustomer.executeUpdate();
            
            // 4. Commit transaction jika semua berhasil
            if (rowsAccount > 0 && rowsCustomer > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            
            // Rollback jika ada error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
            
        } finally {
            // Close resources
            try {
                if (stmtAccount != null) stmtAccount.close();
                if (stmtCustomer != null) stmtCustomer.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Fungsi untuk LOGIN - Validasi username dan password
    public User loginUser(String username, String password) {
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
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Gunakan constructor yang sesuai dengan class User Anda
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
                return user;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Jangan throw RuntimeException, return null saja
        } finally {
            // Close resources
            try { 
                if (rs != null) rs.close(); 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
            try { 
                if (stmt != null) stmt.close(); 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
            try { 
                if (conn != null) conn.close(); 
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
        }
    }
        
    // Fungsi untuk CEK username sudah ada atau belum
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM account WHERE Username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                return count > 0;
            }
            
            rs.close();
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Fungsi untuk GENERATE Account ID
    private String generateAccountID(Connection conn) throws SQLException {
        String sql = "SELECT ID FROM account ORDER BY ID DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        String newID = "ACC000001"; // Default ID pertama
        
        if (rs.next()) {
            String lastID = rs.getString("ID");
            // Extract angka dari ACC000001 -> 1
            int num = Integer.parseInt(lastID.substring(3));
            num++; // Increment
            // Format kembali ke ACC000002
            newID = String.format("ACC%06d", num);
        }
        
        rs.close();
        stmt.close();
        return newID;
    }
    
    // Fungsi untuk GENERATE Customer ID
    private String generateCustomerID(Connection conn) throws SQLException {
        String sql = "SELECT ID FROM customers ORDER BY ID DESC LIMIT 1";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        String newID = "CUS0001"; // Default ID pertama
        
        if (rs.next()) {
            String lastID = rs.getString("ID");
            // Extract angka dari CUS0001 -> 1
            int num = Integer.parseInt(lastID.substring(3));
            num++; // Increment
            // Format kembali ke CUS0002
            newID = String.format("CUS%04d", num);
        }
        
        rs.close();
        stmt.close();
        return newID;
    }
    
    // Fungsi untuk GET user by username (untuk profile)
    public User getUserByUsername(String username) {
        String sql = "SELECT a.ID as accountID, a.Username, a.Type, " +
                    "c.ID as customerID, c.Name, c.Gender, c.Birth_Date, c.Phone_Number, c.Membership_ID " +
                    "FROM account a " +
                    "LEFT JOIN customers c ON a.ID = c.Account_ID " +
                    "WHERE a.Username = ?";
        
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
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
                rs.close();
                return user;
            }
            
            rs.close();
            return null;
            
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Fungsi untuk UPDATE user profile
    public boolean updateUser(User user) {
        String sql = "UPDATE customers c " +
                     "JOIN account a ON c.Account_ID = a.ID " +
                     "SET c.Name = ?, c.Gender = ?, c.Birth_Date = ?, c.Phone_Number = ? " +
                     "WHERE a.Username = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
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
