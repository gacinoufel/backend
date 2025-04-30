package com.example.backend.dtos.orderitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDTO {

    private Long orderId;
    private Long productId;
    private Integer quantity;
}
