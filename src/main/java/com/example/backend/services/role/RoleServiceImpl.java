package com.example.backend.services.role;

import com.example.backend.dtos.role.RoleRequestDTO;
import com.example.backend.dtos.role.RoleResponseDTO;
import com.example.backend.entities.Role;
import com.example.backend.exceptions.RoleNotFoundException;
import com.example.backend.mappers.RoleMapper;
import com.example.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        Role role = roleMapper.fromRequestDTOToEntity(roleRequestDTO);
        Role savedRole = roleRepository.save(role);
        return roleMapper.fromEntityToResponseDTO(savedRole);
    }

    @Override
    public RoleResponseDTO getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + roleId));
        return roleMapper.fromEntityToResponseDTO(role);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::fromEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponseDTO updateRole(Long roleId, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + roleId));

        roleMapper.updateEntityFromRequestDTO(roleRequestDTO, role);
        Role updatedRole = roleRepository.save(role);
        return roleMapper.fromEntityToResponseDTO(updatedRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            throw new RoleNotFoundException("Role not found with ID: " + roleId);
        }
        roleRepository.deleteById(roleId);
    }
}
