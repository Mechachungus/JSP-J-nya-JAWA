package com.example.dao;

import com.example.models.User;
import com.example.utils.DBConnection;
import java.sql.*;

public class UserDAO {
    
    // Fungsi untuk REGISTER - Insert user baru (Account + Customer)
    public boolean registerUser(User user, String password) {
        System.out.println("=== DEBUG USERDAO - REGISTER USER ===");
        System.out.println("User: " + user.getUsername() + ", " + user.getFullName());
        
        Connection conn = null;
        PreparedStatement stmtAccount = null;
        PreparedStatement stmtCustomer = null;
        
        try {
            System.out.println("1. Getting database connection...");
            conn = DBConnection.getConnection();
            
            if (conn == null) {
                System.err.println("❌ DATABASE CONNECTION IS NULL!");
                return false;
            }
            
            System.out.println("2. Starting transaction...");
            conn.setAutoCommit(false); // Start transaction
            
            // 1. Generate ID untuk Account dan Customer
            System.out.println("3. Generating Account ID...");
            String accountID = generateAccountID(conn);
            System.out.println("4. Generating Customer ID...");
            String customerID = generateCustomerID(conn);
            
            // 2. Insert ke table ACCOUNT
            System.out.println("5. Inserting into ACCOUNT table...");
            String sqlAccount = "INSERT INTO account (ID, Username, Password, Type) VALUES (?, ?, ?, ?)";
            stmtAccount = conn.prepareStatement(sqlAccount);
            stmtAccount.setString(1, accountID);
            stmtAccount.setString(2, user.getUsername());
            stmtAccount.setString(3, password);
            stmtAccount.setString(4, "Customer");
            
            System.out.println("Account SQL: " + sqlAccount);
            System.out.println("Parameters: " + accountID + ", " + user.getUsername() + ", [PASSWORD], Customer");
            
            int rowsAccount = stmtAccount.executeUpdate();
            System.out.println("6. Account insert - Rows affected: " + rowsAccount);
            
            // 3. Insert ke table CUSTOMERS
            System.out.println("7. Inserting into CUSTOMERS table...");
            String sqlCustomer = "INSERT INTO customers (ID, Name, Gender, Birth_Date, Phone_Number, Account_ID, Membership_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmtCustomer = conn.prepareStatement(sqlCustomer);
            stmtCustomer.setString(1, customerID);
            stmtCustomer.setString(2, user.getFullName());
            stmtCustomer.setString(3, user.getGender());
            stmtCustomer.setString(4, user.getBirthDate());
            stmtCustomer.setString(5, user.getPhoneNumber());
            stmtCustomer.setString(6, accountID);
            stmtCustomer.setString(7, "M1");
            
            System.out.println("Customer SQL: " + sqlCustomer);
            System.out.println("Parameters: " + customerID + ", " + user.getFullName() + ", " + user.getGender() + ", " + user.getBirthDate() + ", " + user.getPhoneNumber() + ", " + accountID + ", M1");
            
            int rowsCustomer = stmtCustomer.executeUpdate();
            System.out.println("8. Customer insert - Rows affected: " + rowsCustomer);
            
            // 4. Commit transaction jika semua berhasil
            if (rowsAccount > 0 && rowsCustomer > 0) {
                System.out.println("9. Committing transaction...");
                conn.commit();
                System.out.println("✅ USER REGISTERED SUCCESSFULLY!");
                System.out.println("   Account ID: " + accountID);
                System.out.println("   Customer ID: " + customerID);
                return true;
            } else {
                System.err.println("❌ Failed to register user - rolling back");
                System.err.println("   Account rows: " + rowsAccount + ", Customer rows: " + rowsCustomer);
                conn.rollback();
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ SQL Error registering user: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            
            // Rollback jika ada error
            if (conn != null) {
                try {
                    System.err.println("Performing rollback...");
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Rollback failed: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            return false;
            
        } finally {
            // Close resources
            try {
                if (stmtAccount != null) {
                    stmtAccount.close();
                    System.out.println("Account statement closed");
                }
                if (stmtCustomer != null) {
                    stmtCustomer.close();
                    System.out.println("Customer statement closed");
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    System.out.println("Connection auto-commit restored");
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    
    // Fungsi untuk LOGIN - Validasi username dan password
    public User loginUser(String username, String password) {
        System.out.println("🔍 Attempting Login for: " + username);

        // FIX: Changed INNER JOIN to LEFT JOIN so we can see if account exists even if profile is missing
        String sql = "SELECT "
                    + "a.ID AS accountID, "
                    + "c.ID AS customerID, "
                    + "a.Username, "
                    + "c.Name AS FullName, "
                    + "a.Type AS accountType, "
                    + "m.ID AS membershipID "
                    + "FROM account a "
                    + "LEFT JOIN customers c ON a.ID = c.Account_ID "
                    + "LEFT JOIN membership m ON c.Membership_ID = m.ID "
                    + "WHERE a.Username = ? AND a.Password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ User Found! Account ID: " + rs.getString("accountID"));
                    
                    User user = new User();
                    user.setAccountID(rs.getString("accountID"));
                    user.setUsername(rs.getString("Username"));
                    user.setAccountType(rs.getString("accountType"));
                    
                    // Handle potential NULLs from LEFT JOIN
                    user.setCustomerID(rs.getString("customerID")); // Might be null
                    user.setFullName(rs.getString("FullName"));     // Might be null
                    user.setMembershipID(rs.getString("membershipID")); // Might be null

                    return user; 
                } else {
                    System.out.println("❌ Login Failed: User not found or password incorrect.");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database Error during Login: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
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
                return count > 0;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Fungsi untuk GENERATE Account ID
private String generateAccountID(Connection conn) throws SQLException {
        System.out.println("=== GENERATE ACCOUNT ID ===");
        String sql = "SELECT ID FROM account ORDER BY ID DESC LIMIT 1";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            System.out.println("Executing: " + sql);
            rs = stmt.executeQuery(sql);
            
            String newID = "ACC000001"; // Default ID pertama
            
            if (rs.next()) {
                String lastID = rs.getString("ID");
                System.out.println("Last Account ID found: " + lastID);
                
                // Extract angka dari ACC000001 -> 1
                int num = Integer.parseInt(lastID.substring(3));
                num++; // Increment
                // Format kembali ke ACC000002
                newID = String.format("ACC%06d", num);
                System.out.println("Incremented Account ID: " + newID);
            } else {
                System.out.println("No existing Account ID, using default: " + newID);
            }
            
            return newID;
            
        } finally {
            if (rs != null) {
                rs.close();
                System.out.println("Account ID ResultSet closed");
            }
            if (stmt != null) {
                stmt.close();
                System.out.println("Account ID Statement closed");
            }
        }
    }
    
    // Fungsi untuk GENERATE Customer ID
    private String generateCustomerID(Connection conn) throws SQLException {
        System.out.println("=== GENERATE CUSTOMER ID ===");
        String sql = "SELECT ID FROM customers ORDER BY ID DESC LIMIT 1";
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            System.out.println("Executing: " + sql);
            rs = stmt.executeQuery(sql);
            
            String newID = "CUS0001"; // Default ID pertama
            
            if (rs.next()) {
                String lastID = rs.getString("ID");
                System.out.println("Last Customer ID found: " + lastID);
                
                // Extract angka dari CUS0001 -> 1
                int num = Integer.parseInt(lastID.substring(3));
                num++; // Increment
                // Format kembali ke CUS0002
                newID = String.format("CUS%04d", num);
                System.out.println("Incremented Customer ID: " + newID);
            } else {
                System.out.println("No existing Customer ID, using default: " + newID);
            }
            
            return newID;
            
        } finally {
            if (rs != null) {
                rs.close();
                System.out.println("Customer ID ResultSet closed");
            }
            if (stmt != null) {
                stmt.close();
                System.out.println("Customer ID Statement closed");
            }
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