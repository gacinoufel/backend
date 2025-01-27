package com.example.backend.services.auth;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.auth.AuthResponse;
import com.example.backend.dtos.user.UserResponseDTO;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameNotFoundException;
import com.example.backend.security.JwtService;
import com.example.backend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest authRequest) throws UsernameNotFoundException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserResponseDTO user = userService.getUserByUsername(authRequest.getUsername());
            String jwt = jwtService.generateToken(user);
            return new AuthResponse(jwt);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found with username: " + authRequest.getUsername());
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public UserResponseDTO register(AuthRequest authRequest) {
        return userService.createUser(authRequest);
    }
}
