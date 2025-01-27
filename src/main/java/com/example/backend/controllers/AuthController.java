package com.example.backend.controllers;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.auth.AuthResponse;
import com.example.backend.dtos.user.UserResponseDTO;
import com.example.backend.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication and registration")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate a user and return a JWT token if credentials are valid")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.login(authRequest);
        return ResponseEntity.ok(authResponse); // 200 OK
    }

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user in the system")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody AuthRequest authRequest) {
        UserResponseDTO userResponseDTO = authService.register(authRequest);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED); // 201 Created
    }
}
