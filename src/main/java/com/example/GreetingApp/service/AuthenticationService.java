package com.example.GreetingApp.service;

import com.example.GreetingApp.DTO.AuthUserDTO;
import com.example.GreetingApp.DTO.ResponseDTO;
import com.example.GreetingApp.SecurityConfig.jutil;
import com.example.GreetingApp.model.AuthUser;
import com.example.GreetingApp.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private jutil jutil;

    // Secret key for JWT

    // Method for user registration
    @Transactional
    public ResponseDTO register(AuthUserDTO authUserDTO) {
        // Check if the email already exists
        if (authUserRepository.findByEmail(authUserDTO.getEmail()).isPresent()) {
            return new ResponseDTO("error", "Email already in use");
        }

        // Create and save the user
        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName());
        user.setEmail(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));  // Encrypt the password

        // Save user to the database
        authUserRepository.save(user);

        // Generate JWT Token
        String token = jutil.generateToken(user);

        // Send Email Notification to the User
        // emailService.sendEmailNotification(user.getEmail(), token);
        return new ResponseDTO("success", "User registered successfully. A verification token has been sent to your email.");
    }
}