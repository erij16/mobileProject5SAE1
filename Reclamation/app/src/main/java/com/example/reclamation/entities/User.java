package com.example.reclamation.entities;

public class User {
    public static final User DEFAULT_USER = new User(1, "defaultUser", "default@example.com", "55586868");

    private int id;
    private String username;
    private String email;
    private String phoneNumber;

    public User(int id, String username, String email , String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

