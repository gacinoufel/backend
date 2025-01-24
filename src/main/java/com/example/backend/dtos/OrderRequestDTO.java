package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderRequestDTO {

    private Long userId;
    private Set<OrderItemRequestDTO> items;
}
