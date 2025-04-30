package com.example.backend.mappers;

import com.example.backend.dtos.order.OrderRequestDTO;
import com.example.backend.dtos.order.OrderResponseDTO;
import com.example.backend.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "items", target = "items")
    OrderResponseDTO fromEntityToResponseDTO(Order order);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "items", target = "items")
    Order fromRequestDTOToEntity(OrderRequestDTO orderRequestDTO);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "items", target = "items")
    void updateEntityFromRequestDTO(OrderRequestDTO orderRequestDTO, @MappingTarget Order order);

}
