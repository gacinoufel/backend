package com.example.backend.mappers;

import com.example.backend.dtos.auth.AuthRequest;
import com.example.backend.dtos.user.UserRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthMapper INSTANCE = Mappers.getMapper(AuthMapper.class);

    @Mapping(target = "roleIds", ignore = true)
    UserRequestDTO authRequestToUserRequestDTO(AuthRequest authRequest);

    @Mapping(target = "password", source = "password")
    @Mapping(target = "username", source = "username")
    AuthRequest userRequestDTOToAuthRequest(UserRequestDTO userRequestDTO);
}