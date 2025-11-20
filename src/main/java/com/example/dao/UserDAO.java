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
            stmtCustomer.setString(6, accountID); // Foreign Key ke table Account
            stmtCustomer.setString(7, "MEM001"); // Default Membership ID

            int rowsCustomer = stmtCustomer.executeUpdate();

            // 4. Cek apakah kedua operasi berhasil
            if (rowsAccount > 0 && rowsCustomer > 0) {
                conn.commit(); // Commit transaksi jika kedua insert berhasil
                success = true;
            } else {
                conn.rollback(); // Rollback jika ada yang gagal
                System.err.println("Gagal commit. Melakukan rollback.");
            }

        } catch (SQLException e) {
            // PERBAIKAN: Cek conn != null sebelum rollback
            if (conn != null) {
                try {
                    System.err.println("Transaction error. Initiating rollback...");
                    conn.rollback(); 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Error saat mendaftar pengguna: " + e.getMessage());
            e.printStackTrace();
            success = false;
        } finally {
            // PERBAIKAN: Cek objek != null sebelum close()
            try {
                if (stmtCustomer != null) {
                    stmtCustomer.close();
                }
                if (stmtAccount != null) {
                    stmtAccount.close();
                }
                if (conn != null) {
                    // Reset auto-commit ke true sebelum menutup koneksi
                    conn.setAutoCommit(true); 
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
    
    // Fungsi untuk LOGIN - Validasi username dan password
public User loginUser(String username, String password) {
        // Query SQL yang menggabungkan Account, Customers, dan Membership
        String sql = "SELECT "
                   + "a.ID AS accountID, "
                   + "c.ID AS customerID, "
                   + "a.Username, "
                   + "c.Name AS FullName, "
                   + "a.Type AS accountType, "
                   + "m.ID AS membershipID "
                   + "FROM account a "
                   + "INNER JOIN customers c ON a.ID = c.Account_ID "
                   + "INNER JOIN membership m ON c.Membership_ID = m.ID "
                   + "WHERE a.Username = ? AND a.Password = ?";

        // 💡 Menggunakan try-with-resources untuk menutup conn dan stmt secara otomatis
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameter untuk query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // 💡 Menggunakan try-with-resources untuk menutup rs secara otomatis
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Login berhasil: Buat objek User dan isi data
                    User user = new User();
                    user.setAccountID(rs.getString("accountID"));
                    user.setCustomerID(rs.getString("customerID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setAccountType(rs.getString("accountType"));
                    user.setMembershipID(rs.getString("membershipID"));

                    return user; // Login sukses
                }
            }
        } catch (SQLException e) {
            // Tangani error database (koneksi, query, dll.)
            System.err.println("Error saat menjalankan login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null; // Login gagal (kredensial salah atau terjadi error)
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
