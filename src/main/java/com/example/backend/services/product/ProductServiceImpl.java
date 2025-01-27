package com.example.backend.services.product;

import com.example.backend.dtos.product.ProductRequestDTO;
import com.example.backend.dtos.product.ProductResponseDTO;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.ProductNotFoundException;
import com.example.backend.mappers.ProductMapper;
import com.example.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.fromRequestDTOToEntity(productRequestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.fromEntityToResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        return productMapper.fromEntityToResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::fromEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        productMapper.updateEntityFromRequestDTO(productRequestDTO, product);
        Product updatedProduct = productRepository.save(product);
        return productMapper.fromEntityToResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }
}
