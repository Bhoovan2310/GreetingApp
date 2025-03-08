package com.example.GreetingApp.DTO;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String currentPassword;
    private String newPassword;
}
