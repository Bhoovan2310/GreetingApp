package com.example.GreetingApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to send an email notification
    public void sendEmailNotification(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Welcome to Our Platform - Please Verify Your Email");
        message.setText("Hello and welcome to our community!\n\n"
                + "Thank you for registering with us. We're excited to have you on board.\n\n"
                + "To complete your registration and activate your account, please verify your email address by using the following token:\n\n"
                + "Verification Token: " + token + "\n\n"
                + "If you did not register for an account, please ignore this message.\n\n"
                + "Best regards,\n"
                + "The GreetingAPP Team");
        mailSender.send(message);
    }
    public void sendLoginNotification(String recipientEmail) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipientEmail);
        emailMessage.setSubject("Successful Login Notification");
        emailMessage.setText("Dear User,\n\nYou have successfully logged into your account. If this was not you, please contact support immediately.\n\nBest Regards,\nThe GreetingApp");

        mailSender.send(emailMessage);
        System.out.println("Login notification email successfully sent to: " + recipientEmail);
    }

    public void sendPasswordChangedNotification(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Changed Confirmation");
        message.setText("Your password has been successfully changed. If you didn't request this, please contact support immediately.");
        mailSender.send(message);
        System.out.println("Main send success");
    }


}
