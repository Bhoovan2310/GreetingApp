package com.example.GreetingApp.service;

import com.example.GreetingApp.DTO.AuthUserDTO;
import com.example.GreetingApp.DTO.LoginDTO;
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
    private emailService email;

    @Autowired
    private jutil jutil;



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
        email.sendEmailNotification(user.getEmail(), token);
        return new ResponseDTO("success", "User registered successfully. A verification token has been sent to your email.");
    }

    // Method for user login
    public ResponseDTO login(LoginDTO loginDTO) {
        // Find the user by email
        AuthUser user = authUserRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify the password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ResponseDTO("error", "Invalid password");
        }

        // Generate a new JWT token
        String token = jutil.generateToken(user);

        // Send login notification (this could be an email or SMS)
        email.sendLoginNotification(user.getEmail());

        return new ResponseDTO("success", "User logged in successfully.", token);
    }


}
