package com.example.backend.services.orderitem;

import com.example.backend.dtos.OrderItemDTO;
import java.util.List;

public interface OrderItemService {

    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    OrderItemDTO getOrderItemById(Long orderItemId);

    List<OrderItemDTO> getAllOrderItems();

    void deleteOrderItem(Long orderItemId);

    OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO);
}
