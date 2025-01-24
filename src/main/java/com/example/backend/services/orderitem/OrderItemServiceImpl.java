package com.example.backend.services.orderitem;

import com.example.backend.dtos.OrderItemRequestDTO;
import com.example.backend.dtos.OrderItemResponseDTO;
import com.example.backend.entities.Order;
import com.example.backend.entities.OrderItem;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.OrderItemNotFoundException;
import com.example.backend.exceptions.OrderNotFoundException;
import com.example.backend.exceptions.ProductNotFoundException;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO) {
        Order order = orderRepository.findById(orderItemRequestDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderItemRequestDTO.getOrderId()));

        Product product = productRepository.findById(orderItemRequestDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + orderItemRequestDTO.getProductId()));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemRequestDTO.getQuantity());

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return convertToDTO(savedOrderItem);
    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId));
        return convertToDTO(orderItem);
    }

    @Override
    public List<OrderItemResponseDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItemResponseDTO updateOrderItem(Long orderItemId, OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId));

        if (orderItemRequestDTO.getOrderId() != null) {
            Order order = orderRepository.findById(orderItemRequestDTO.getOrderId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderItemRequestDTO.getOrderId()));
            orderItem.setOrder(order);
        }

        if (orderItemRequestDTO.getProductId() != null) {
            Product product = productRepository.findById(orderItemRequestDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + orderItemRequestDTO.getProductId()));
            orderItem.setProduct(product);
        }

        if (orderItemRequestDTO.getQuantity() != null) {
            orderItem.setQuantity(orderItemRequestDTO.getQuantity());
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return convertToDTO(updatedOrderItem);
    }

    private OrderItemResponseDTO convertToDTO(OrderItem orderItem) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setId(orderItem.getId());
        dto.setOrderId(orderItem.getOrder().getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }
}
