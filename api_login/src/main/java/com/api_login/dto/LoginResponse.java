package com.api_login.dto;

import com.api_login.model.Role;

public class LoginResponse {
    private String message;
    private String role;
    private String token;

    public LoginResponse(String message, String role, String token) {
        this.message = message;
        this.role = role;
        this.token = token;
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
