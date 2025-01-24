package com.example.backend.dtos;

import lombok.Data;

@Data
public class OrderItemRequestDTO {

    private Long orderId;
    private Long productId;
    private Integer quantity;
}
