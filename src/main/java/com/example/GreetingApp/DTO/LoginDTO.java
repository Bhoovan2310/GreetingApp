package com.example.GreetingApp.DTO;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
