package com.example.GreetingApp.SecurityConfig;

import com.example.GreetingApp.model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class jutil {
    private final String JWT_SECRET = "qwertyuiopokijuhgfrdeszxcvghuikjhgfdszvbnjiolkjhgfdszxcvbnj";  // Replace with a secure secret key

    // Method to generate a JWT token
    @SuppressWarnings("deprecation")
    public String generateToken(AuthUser user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // 24 hours
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

}
