package com.example.backend.services.user;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.user.UserRequestDTO;
import com.example.backend.dtos.user.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(AuthRequest authRequest);

    UserResponseDTO getUserById(Long userId);

    UserResponseDTO getUserByUsername(String username);

    UserDetails loadUserByUsername(String username);

    List<UserResponseDTO> getAllUsers();

    void deleteUser(Long userId);

    UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO);
}
