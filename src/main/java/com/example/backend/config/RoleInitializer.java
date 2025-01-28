package com.example.backend.config;

import com.example.backend.entities.Role;
import com.example.backend.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {


    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRoleName("USER").isEmpty()) {
                Role userRole = new Role();
                userRole.setRoleName("USER");
                roleRepository.save(userRole);
            }
            if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setRoleName("ADMIN");
                roleRepository.save(adminRole);
            }
        };
    }
}
