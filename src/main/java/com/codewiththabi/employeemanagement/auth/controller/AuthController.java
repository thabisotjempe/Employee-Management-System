package com.codewiththabi.employeemanagement.auth.controller;

import com.codewiththabi.employeemanagement.auth.dto.LoginResponse;
import com.codewiththabi.employeemanagement.auth.dto.LoginRequest;
import com.codewiththabi.employeemanagement.auth.dto.RegisterRequest;
import com.codewiththabi.employeemanagement.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);
        return ResponseEntity.ok(response);
    }
}
