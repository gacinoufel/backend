package com.example.backend.services.user;

import com.example.backend.dtos.AuthRequest;
import com.example.backend.dtos.UserDTO;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDTO createUser(AuthRequest authRequest);

    UserDTO getUserById(Long userId);

    UserDTO getUserByUsername(String username);

    UserDetails loadUserByUsername(String username);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);
}
