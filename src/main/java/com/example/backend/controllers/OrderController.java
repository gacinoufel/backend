package com.example.backend.controllers;

import com.example.backend.dtos.order.OrderRequestDTO;
import com.example.backend.dtos.order.OrderResponseDTO;
import com.example.backend.services.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create an order", description = "Creates a new order")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) { // Ajout de @Valid pour la validation
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @Parameter(description = "ID of the order to retrieve", required = true) @PathVariable Long id) {
        OrderResponseDTO order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order", description = "Update an existing order by its ID")
    public ResponseEntity<OrderResponseDTO> updateOrder(
            @Parameter(description = "ID of the order to update", required = true) @PathVariable Long id,
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) { // Ajout de @Valid pour la validation
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, orderRequestDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID of the order to delete", required = true) @PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
