package com.example.backend.controllers;

import com.example.backend.dtos.orderitem.OrderItemRequestDTO;
import com.example.backend.dtos.orderitem.OrderItemResponseDTO;
import com.example.backend.services.orderitem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
@Tag(name = "OrderItem", description = "Operations related to order items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Create an order item", description = "Creates a new order item")
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@RequestBody OrderItemRequestDTO orderItemRequestDTO) {
        OrderItemResponseDTO createdOrderItem = orderItemService.createOrderItem(orderItemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order item by ID", description = "Retrieve an order item by its ID")
    public ResponseEntity<OrderItemResponseDTO> getOrderItemById(
            @Parameter(description = "ID of the order item to retrieve", required = true) @PathVariable Long id) {
        OrderItemResponseDTO orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    @GetMapping
    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items")
    public ResponseEntity<List<OrderItemResponseDTO>> getAllOrderItems() {
        List<OrderItemResponseDTO> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order item", description = "Update an existing order item by its ID")
    public ResponseEntity<OrderItemResponseDTO> updateOrderItem(
            @Parameter(description = "ID of the order item to update", required = true) @PathVariable Long id,
            @RequestBody OrderItemRequestDTO orderItemRequestDTO) {
        OrderItemResponseDTO updatedOrderItem = orderItemService.updateOrderItem(id, orderItemRequestDTO);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item", description = "Delete an order item by its ID")
    public ResponseEntity<Void> deleteOrderItem(
            @Parameter(description = "ID of the order item to delete", required = true) @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}