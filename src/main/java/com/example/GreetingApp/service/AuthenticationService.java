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

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
     AuthUserRepository authUserRepository;

    @Autowired
     BCryptPasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService ;

    @Autowired
     jutil jutil;

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
        emailService.sendEmailNotification(user.getEmail(), token);

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
        emailService.sendLoginNotification(user.getEmail());
        return new ResponseDTO("success", "User logged in successfully.", token);
    }

    public boolean changePassword(String email , String newPassword){
        Optional<AuthUser> temp = authUserRepository.findByEmail(email);

        if (temp.isPresent()){
            AuthUser user = temp.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(user);
            //send the mail to user
            emailService.sendPasswordChangedNotification(email);
            return true;
        }

        return false;
    }

    public boolean resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> optionalUser = authUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            AuthUser user = optionalUser.get();

            // Validate current password
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new RuntimeException("Current password is incorrect!");
            }

            // Update password with the new hashed one
            user.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(user);
            //send email to user
            emailService.sendPasswordResetNotification(email);
            return true;
        }
        throw new RuntimeException("User not found with email: " + email);
    }

}
