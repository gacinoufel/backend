package com.example.backend.dtos.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequestDTO {

    private String username;
    private String password;
    private Set<Long> roleIds;
}