package com.example.backend.mappers;

import com.example.backend.dtos.role.RoleRequestDTO;
import com.example.backend.dtos.role.RoleResponseDTO;
import com.example.backend.entities.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponseDTO fromEntityToResponseDTO(Role role);

    Role fromRequestDTOToEntity(RoleRequestDTO roleRequestDTO);

    void updateEntityFromRequestDTO(RoleRequestDTO roleRequestDTO, @MappingTarget Role role);
}
