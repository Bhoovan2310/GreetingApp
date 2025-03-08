package com.example.GreetingApp.controller;

import com.example.GreetingApp.DTO.AuthUserDTO;
import com.example.GreetingApp.DTO.ForgotPasswordRequestDTO;
import com.example.GreetingApp.DTO.LoginDTO;
import com.example.GreetingApp.DTO.ResponseDTO;
import com.example.GreetingApp.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
     AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid AuthUserDTO authUserDTO) {
        ResponseDTO response = authenticationService.register(authUserDTO);
        return ResponseEntity.status(response.getStatus().equals("success") ? 200 : 400).body(response);
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) {
        ResponseDTO response = authenticationService.login(loginDTO);
        return ResponseEntity.status(response.getStatus().equals("success") ? 200 : 400).body(response);
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email, @RequestBody ForgotPasswordRequestDTO request) {
        boolean isChanged = authenticationService.changePassword(email , request.getPassword());
        if (isChanged) {
            return ResponseEntity.ok().body("{\"message\": \"Password has been changed successfully!\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"message\": \"Sorry! We cannot find the user email: " + email + "\"}");
        }
    }
}
