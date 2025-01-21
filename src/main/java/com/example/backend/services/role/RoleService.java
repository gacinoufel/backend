package com.example.backend.services.role;

import com.example.backend.dtos.RoleDTO;
import com.example.backend.entities.enums.RoleType;

import java.util.List;

public interface RoleService {

    RoleDTO createRole(RoleDTO roleDTO);

    RoleDTO getRoleById(Long roleId);

    RoleDTO getRoleByRoleType(RoleType roleType);

    List<RoleDTO> getAllRoles();

    void deleteRole(Long roleId);

    RoleDTO updateRole(Long roleId, RoleDTO roleDTO);
}
