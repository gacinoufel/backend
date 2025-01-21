package com.example.backend.services.user;

import com.example.backend.dtos.AuthRequest;
import com.example.backend.dtos.UserDTO;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.backend.exceptions.UsernameAlreadyExistsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapperUtils modelMapperUtils;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            ModelMapperUtils modelMapperUtils,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapperUtils = modelMapperUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(AuthRequest authRequest) throws UsernameAlreadyExistsException {
        if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + authRequest.getUsername());
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        Role userRole = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRole(userRole);

        User savedUser = userRepository.save(user);
        return modelMapperUtils.getModelMapper().map(savedUser, AuthRequest.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return modelMapperUtils.getModelMapper().map(user, AuthRequest.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return modelMapperUtils.getModelMapper().map(user, AuthRequest.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleName())
                .build();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return modelMapperUtils.mapList(users, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        user.setUsername(userDTO.getUsername());
        User updatedUser = userRepository.save(user);
        return modelMapperUtils.getModelMapper().map(updatedUser, UserDTO.class);
    }
}
