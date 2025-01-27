package com.example.backend.services.role;

import com.example.backend.dtos.role.RoleRequestDTO;
import com.example.backend.dtos.role.RoleResponseDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO getRoleById(Long roleId);

    List<RoleResponseDTO> getAllRoles();

    void deleteRole(Long roleId);

    RoleResponseDTO updateRole(Long roleId, RoleRequestDTO roleRequestDTO);
}
