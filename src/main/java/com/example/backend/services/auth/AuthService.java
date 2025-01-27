package com.example.backend.services.auth;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.auth.AuthResponse;
import com.example.backend.dtos.user.UserResponseDTO;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameNotFoundException;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest) throws UsernameNotFoundException, InvalidCredentialsException;

    UserResponseDTO register(AuthRequest authRequest);
}
