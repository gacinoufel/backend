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
import com.example.backend.exceptions.RoleNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
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

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    private UserDTO convertToDTO(User user) {
        return modelMapperUtils.getModelMapper().map(user, UserDTO.class);
    }

    @Override
    public UserDTO createUser(AuthRequest authRequest) throws RoleNotFoundException {
        if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            throw new RoleNotFoundException("Username already exists: " + authRequest.getUsername());
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        Role userRole = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new RoleNotFoundException("Role USER not found"));
        user.setRole(userRole);

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return convertToDTO(user);
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
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = findUserById(userId);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        user.setUsername(userDTO.getUsername());
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }
}
