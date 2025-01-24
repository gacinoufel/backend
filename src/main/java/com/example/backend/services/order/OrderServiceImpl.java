package com.example.backend.services.order;

import com.example.backend.dtos.OrderRequestDTO;
import com.example.backend.dtos.OrderResponseDTO;
import com.example.backend.entities.Order;
import com.example.backend.entities.User;
import com.example.backend.exceptions.OrderNotFoundException;
import com.example.backend.exceptions.UserNotFoundException;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapperUtils modelMapperUtils;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            UserRepository userRepository,
            ModelMapperUtils modelMapperUtils) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    private OrderResponseDTO convertToDTO(Order order) {
        return modelMapperUtils.getModelMapper().map(order, OrderResponseDTO.class);
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = modelMapperUtils.getModelMapper().map(orderRequestDTO, Order.class);

        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + orderRequestDTO.getUserId()));
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = findOrderById(orderId);
        return convertToDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = findOrderById(orderId);
        modelMapperUtils.getModelMapper().map(orderRequestDTO, order);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }
}
