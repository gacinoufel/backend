package com.example.backend.dtos.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private Integer qteStock;
    private double price;
    private String description;
}
