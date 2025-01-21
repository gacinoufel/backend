package com.example.backend.config;

import com.example.backend.entities.Role;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    // Remplissage de la bdd avec les 2 types de role ADMIN et USER
    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRoleType(RoleType.USER).isEmpty()) {
                Role userRole = new Role();
                userRole.setRoleType(RoleType.USER);
                roleRepository.save(userRole);
            }
            if (roleRepository.findByRoleType(RoleType.ADMIN).isEmpty()) {
                Role adminRole = new Role();
                adminRole.setRoleType(RoleType.ADMIN);
                roleRepository.save(adminRole);
            }
        };
    }
}
