package com.example.GreetingApp.model;

import jakarta.persistence.*;
import lombok.Data;

    @Entity
    @Data
    @Table(name = "auth_users")  // You can change the table name as per your requirements
    public class AuthUser {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;
    }
