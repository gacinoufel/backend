package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest extends UserDTO {

    private String roleType;
}
