package com.example.backend.services.role;

import com.example.backend.dtos.RoleDTO;
import com.example.backend.entities.Role;
import com.example.backend.entities.enums.RoleType;
import com.example.backend.repositories.RoleRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;
import com.example.backend.exceptions.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapperUtils modelMapperUtils;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapperUtils modelMapperUtils) {
        this.roleRepository = roleRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + roleId));
    }

    private RoleDTO convertToDTO(Role role) {
        return modelMapperUtils.getModelMapper().map(role, RoleDTO.class);
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        Role role = findRoleById(roleId);
        return convertToDTO(role);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapperUtils.getModelMapper().map(roleDTO, Role.class);
        Role savedRole = roleRepository.save(role);
        return convertToDTO(savedRole);
    }

    @Override
    public RoleDTO getRoleByRoleType(RoleType roleType) {
        Role role = roleRepository.findByRoleType(roleType)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with type: " + roleType));
        return convertToDTO(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException("Role not found with ID: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }

    @Override
    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO) {
        Role role = findRoleById(roleId);
        modelMapperUtils.getModelMapper().map(roleDTO, role);
        Role updatedRole = roleRepository.save(role);
        return convertToDTO(updatedRole);
    }
}
