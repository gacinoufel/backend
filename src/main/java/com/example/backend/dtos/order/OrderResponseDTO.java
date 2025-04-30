package com.example.backend.dtos.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO extends OrderRequestDTO {

    private Long orderId;
}
