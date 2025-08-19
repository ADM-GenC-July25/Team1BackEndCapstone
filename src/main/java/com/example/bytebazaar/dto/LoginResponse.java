package com.example.bytebazaar.dto;

public class LoginResponse {
    private String token;
    private String username;
    private String email;
    private String accessLevel;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String token, String username, String email, String accessLevel) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.accessLevel = accessLevel;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
