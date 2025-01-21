package com.example.backend.dtos;

import com.example.backend.entities.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {

    private Long id;
    private RoleType roleType;
}
