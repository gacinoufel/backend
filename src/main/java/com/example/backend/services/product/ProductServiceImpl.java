package com.example.backend.services.product;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.Product;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

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
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = modelMapperUtils.getModelMapper().map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapperUtils.getModelMapper().map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        return modelMapperUtils.getModelMapper().map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapperUtils.getModelMapper().map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        modelMapperUtils.getModelMapper().map(productDTO, product);
        Product updatedProduct = productRepository.save(product);
        return modelMapperUtils.getModelMapper().map(updatedProduct, ProductDTO.class);
    }
}
