package com.example.backend.services.user;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.user.UserRequestDTO;
import com.example.backend.dtos.user.UserResponseDTO;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.exceptions.UsernameNotFoundException;
import com.example.backend.mappers.AuthMapper;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(AuthRequest authRequest) {
        UserRequestDTO userRequestDTO = authMapper.authRequestToUserRequestDTO(authRequest);
        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER not found in the database"));
        User user = userMapper.fromRequestDTOToEntity(userRequestDTO);
        user.setRole(defaultRole);
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.fromEntityToResponseDTO(savedUser);
    }


    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return userMapper.fromEntityToResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::fromEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));
        return userMapper.fromEntityToResponseDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }


    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        userMapper.updateEntityFromRequestDTO(userRequestDTO, user);
        User updatedUser = userRepository.save(user);
        return userMapper.fromEntityToResponseDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
