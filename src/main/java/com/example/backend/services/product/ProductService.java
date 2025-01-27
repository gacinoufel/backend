package com.example.backend.services.product;

import com.example.backend.dtos.product.ProductRequestDTO;
import com.example.backend.dtos.product.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
}
