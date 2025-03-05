package com.example.GreetingApp.controller;

import com.example.GreetingApp.DTO.AuthUserDTO;

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
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid AuthUserDTO authUserDTO) {
        ResponseDTO response = authenticationService.register(authUserDTO);
        return ResponseEntity.status(response.getStatus().equals("success") ? 200 : 400).body(response);
    }

}
