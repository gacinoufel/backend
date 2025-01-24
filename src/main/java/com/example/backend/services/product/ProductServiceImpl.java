package com.example.backend.services.product;

import com.example.backend.dtos.ProductResponseDTO;
import com.example.backend.dtos.ProductRequestDTO;

import com.example.backend.entities.Product;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;
import com.example.backend.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapperUtils modelMapperUtils;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapperUtils modelMapperUtils) {
        this.productRepository = productRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = modelMapperUtils.getModelMapper().map(productRequestDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapperUtils.getModelMapper().map(savedProduct, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        return modelMapperUtils.getModelMapper().map(product, ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapperUtils.getModelMapper().map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        modelMapperUtils.getModelMapper().map(productRequestDTO, product);
        Product updatedProduct = productRepository.save(product);
        return modelMapperUtils.getModelMapper().map(updatedProduct, ProductResponseDTO.class);
    }
}
