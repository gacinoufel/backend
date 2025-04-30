package com.example.backend.dtos.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO extends UserRequestDTO {

    private Long userId;
}
