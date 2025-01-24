package com.example.backend.services.order;

import com.example.backend.dtos.OrderRequestDTO;
import com.example.backend.dtos.OrderResponseDTO;
import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO getOrderById(Long orderId);

    List<OrderResponseDTO> getAllOrders();

    void deleteOrder(Long orderId);

    OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO);
}
