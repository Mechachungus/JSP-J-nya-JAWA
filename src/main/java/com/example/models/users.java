package com.example.models;

public class User {
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String birthDate;
    private String accountType;

    public User(String username, String fullName, String email, String phoneNumber, String gender, String birthDate, String accountType) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
        this.accountType = accountType;
    }

    // Getters are REQUIRED for JSP EL (${user.username}) to work
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getGender() { return gender; }
    public String getBirthDate() { return birthDate; }
    public String getAccountType() { return accountType; }
}