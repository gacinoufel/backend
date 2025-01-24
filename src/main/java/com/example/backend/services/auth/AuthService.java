package com.example.backend.services.auth;

import com.example.backend.dtos.AuthRequest;
import com.example.backend.dtos.AuthResponse;
import com.example.backend.dtos.UserDTO;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameNotFoundException;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest) throws UsernameNotFoundException, InvalidCredentialsException;

    UserDTO register(AuthRequest authRequest);
}
