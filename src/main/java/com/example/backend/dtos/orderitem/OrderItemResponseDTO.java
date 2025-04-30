package com.example.backend.dtos.orderitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDTO extends OrderItemRequestDTO {

    private Long orderItemId;
}
