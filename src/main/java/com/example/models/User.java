package com.example.models;

public class User {
    private String customerID;      // PENTING: Untuk foreign key booking
    private String accountID;       // Account ID
    private String username;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private String birthDate;
    private String accountType;
    private String membershipID;
    
    // Constructor LENGKAP (untuk login)
    public User(String customerID, String accountID, String username, String fullName, 
                String phoneNumber, String gender, String birthDate, 
                String accountType, String membershipID) {
        this.customerID = customerID;
        this.accountID = accountID;
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.accountType = accountType;
        this.membershipID = membershipID;
    }
    
    // Constructor SIMPLE (untuk register)
    public User(String username, String fullName, String phoneNumber, 
                String gender, String birthDate, String accountType) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.accountType = accountType;
    }
    
    // Getters
    public String getCustomerID() { return customerID; }
    public String getAccountID() { return accountID; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getAccountType() { return accountType; }
    public String getMembershipID() { return membershipID; }
}