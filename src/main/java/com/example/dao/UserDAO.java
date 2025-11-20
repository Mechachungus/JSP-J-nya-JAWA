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
        User user = null;
        String sql = "SELECT a.ID AS Account_ID, c.ID AS Customer_ID, a.Username, c.Name, c.Phone_Number, c.Gender, c.Birth_Date, a.Type AS Account_Type, c.Membership_ID " +
                     "FROM account a " +
                     "JOIN customers c ON a.ID = c.Account_ID " +
                     "WHERE a.Username = ? AND a.Password = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Buat object User dari hasil query
                    user = new User(
                        rs.getString("Customer_ID"),
                        rs.getString("Account_ID"),
                        rs.getString("Username"),
                        rs.getString("Name"),
                        rs.getString("Phone_Number"),
                        rs.getString("Gender"),
                        rs.getString("Birth_Date"),
                        rs.getString("Account_Type"),
                        rs.getString("Membership_ID")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error during user login: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
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