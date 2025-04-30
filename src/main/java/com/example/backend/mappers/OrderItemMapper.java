package com.example.backend.mappers;

import com.example.backend.dtos.orderitem.OrderItemRequestDTO;
import com.example.backend.dtos.orderitem.OrderItemResponseDTO;
import com.example.backend.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "order.orderId", target = "orderId")
    @Mapping(source = "product.productId", target = "productId")
    OrderItemResponseDTO fromEntityToResponseDTO(OrderItem orderItem);

    @Mapping(source = "orderId", target = "order.orderId")
    @Mapping(source = "productId", target = "product.productId")
    OrderItem fromRequestDTOToEntity(OrderItemRequestDTO orderItemRequestDTO);

    @Mapping(source = "orderId", target = "order.orderId")
    @Mapping(source = "productId", target = "product.productId")
    void updateEntityFromRequestDTO(OrderItemRequestDTO orderItemRequestDTO, @MappingTarget OrderItem orderItem);
}
