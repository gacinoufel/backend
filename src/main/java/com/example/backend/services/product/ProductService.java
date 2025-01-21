package com.example.backend.services.product;

import com.example.backend.dtos.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long productId);

    List<ProductDTO> getAllProducts();

    void deleteProduct(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);
}
