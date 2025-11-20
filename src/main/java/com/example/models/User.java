package com.example.models;

public class User {
    private String accountID;
    private String customerID;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String gender;
    private String birthDate;
    private String accountType;
    private String membershipID; // Tambahan baru

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

    public User(String username, String fullName, String phoneNumber, String gender, String birthDate, String accountType) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.accountType = accountType;
    }

    // Getter & Setter WAJIB LENGKAP
    public String getAccountID() { return accountID; }
    public void setAccountID(String accountID) { this.accountID = accountID; }

    public String getCustomerID() { return customerID; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
    
    public String getMembershipID() { return membershipID; }
    public void setMembershipID(String membershipID) { this.membershipID = membershipID; }
}