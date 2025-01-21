package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private Long userId;
    private Set<OrderItemDTO> items;
}
