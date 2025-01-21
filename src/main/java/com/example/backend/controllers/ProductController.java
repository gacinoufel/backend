package com.example.backend.controllers;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.services.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Operations related to products")
public class ProductController {

    private final ProductService productService;

    @Autowired

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public")
    @Operation(summary = "Get all products public", description = "Retrieve a list of all products accessible publicly")
    public List<ProductDTO> getAllProductsPublic() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a product by its ID")
    public ProductDTO getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true) @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update an existing product by its ID")
    public ProductDTO updateProduct(
            @Parameter(description = "ID of the product to update", required = true) @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    public void deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true) @PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
