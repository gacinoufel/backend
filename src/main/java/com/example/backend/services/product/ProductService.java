package com.example.backend.services.product;

import com.example.backend.dtos.ProductRequestDTO;

import java.util.List;

import com.example.backend.dtos.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO getProductById(Long productId);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long productId);

    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);
}
