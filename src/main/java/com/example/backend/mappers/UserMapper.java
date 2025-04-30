package com.example.backend.mappers;

import com.example.backend.dtos.user.UserRequestDTO;
import com.example.backend.dtos.user.UserResponseDTO;
import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roleIds")
    UserResponseDTO fromEntityToResponseDTO(User user);

    @Mapping(source = "roleIds", target = "roles")
    User fromRequestDTOToEntity(UserRequestDTO userRequestDTO);

    @Mapping(source = "roleIds", target = "roles")
    void updateEntityFromRequestDTO(UserRequestDTO userRequestDTO, @MappingTarget User user);

    default Set<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleId)
                .collect(Collectors.toSet());
    }

    default Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
        return roleIds.stream()
                .map(id -> {
                    Role role = new Role();
                    role.setRoleId(id);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}