package com.example.backend.services.auth;

import com.example.backend.dtos.AuthRequest;
import com.example.backend.dtos.AuthResponse;
import com.example.backend.dtos.UserDTO;
import com.example.backend.exceptions.InvalidCredentialsException;
import com.example.backend.exceptions.UsernameNotFoundException;
import com.example.backend.security.JwtService;
import com.example.backend.services.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) throws UsernameNotFoundException, InvalidCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            UserDTO user = userService.getUserByUsername(authRequest.getUsername());
            String jwt = jwtService.generateToken(user);
            return new AuthResponse(jwt);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found with username: " + authRequest.getUsername());
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

    @Override
    public UserDTO register(AuthRequest authRequest) {
        return userService.createUser(authRequest);
    }
}
