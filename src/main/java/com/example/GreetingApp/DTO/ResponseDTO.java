package com.example.GreetingApp.DTO;

import lombok.Data;
import lombok.Getter;

@Data
public class ResponseDTO {

    // Getters and Setters
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

