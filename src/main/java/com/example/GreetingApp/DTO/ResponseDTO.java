package com.example.GreetingApp.DTO;

public class ResponseDTO {

    private String status;
    private String message;
    private String token;

    // Constructor for success
    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor for success with token (for login)
    public ResponseDTO(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

