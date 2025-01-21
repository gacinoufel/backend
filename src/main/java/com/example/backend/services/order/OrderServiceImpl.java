package com.example.backend.services.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dtos.OrderDTO;
import com.example.backend.entities.Order;
import com.example.backend.entities.User;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.utils.ModelMapperUtils;

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

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapperUtils.getModelMapper().map(orderDTO, Order.class);

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderDTO.getUserId()));
        order.setUser(user);

        Order savedOrder = orderRepository.save(order);
        return modelMapperUtils.getModelMapper().map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        return modelMapperUtils.getModelMapper().map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapperUtils.getModelMapper().map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        modelMapperUtils.getModelMapper().map(orderDTO, order);
        Order updatedOrder = orderRepository.save(order);
        return modelMapperUtils.getModelMapper().map(updatedOrder, OrderDTO.class);
    }
}
