package com.example.backend.services.order;

import com.example.backend.dtos.order.OrderRequestDTO;
import com.example.backend.dtos.order.OrderResponseDTO;
import com.example.backend.entities.Order;
import com.example.backend.exceptions.OrderNotFoundException;
import com.example.backend.mappers.OrderMapper;
import com.example.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = orderMapper.fromRequestDTOToEntity(orderRequestDTO);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.fromEntityToResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        return orderMapper.fromEntityToResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::fromEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        orderMapper.updateEntityFromRequestDTO(orderRequestDTO, order);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.fromEntityToResponseDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }
}
