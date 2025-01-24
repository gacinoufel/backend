package com.example.backend.services.orderitem;

import com.example.backend.dtos.OrderItemRequestDTO;
import com.example.backend.dtos.OrderItemResponseDTO;
import java.util.List;

public interface OrderItemService {

    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO);

    OrderItemResponseDTO getOrderItemById(Long orderItemId);

    List<OrderItemResponseDTO> getAllOrderItems();

    void deleteOrderItem(Long orderItemId);

    OrderItemResponseDTO updateOrderItem(Long orderItemId, OrderItemRequestDTO orderItemRequestDTO);
}
