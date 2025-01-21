package com.example.backend.services.orderitem;

import com.example.backend.dtos.OrderItemDTO;
import com.example.backend.entities.OrderItem;
import com.example.backend.repositories.OrderItemRepository;
import com.example.backend.utils.ModelMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ModelMapperUtils modelMapperUtils;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ModelMapperUtils modelMapperUtils) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapperUtils.getModelMapper().map(orderItemDTO, OrderItem.class);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return modelMapperUtils.getModelMapper().map(savedOrderItem, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO getOrderItemById(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + orderItemId));
        return modelMapperUtils.getModelMapper().map(orderItem, OrderItemDTO.class);
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItem -> modelMapperUtils.getModelMapper().map(orderItem, OrderItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new RuntimeException("OrderItem not found with ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with ID: " + orderItemId));

        modelMapperUtils.getModelMapper().map(orderItemDTO, orderItem);
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return modelMapperUtils.getModelMapper().map(updatedOrderItem, OrderItemDTO.class);
    }
}
