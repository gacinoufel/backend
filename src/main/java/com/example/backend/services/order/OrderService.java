package com.example.backend.services.order;

import com.example.backend.dtos.OrderDTO;
import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long orderId);

    List<OrderDTO> getAllOrders();

    void deleteOrder(Long orderId);

    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO);
}
