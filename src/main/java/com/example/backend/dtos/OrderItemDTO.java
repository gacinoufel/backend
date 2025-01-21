package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
}
