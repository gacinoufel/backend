package com.example.backend.services.orderitem;

import com.example.backend.dtos.orderitem.OrderItemRequestDTO;
import com.example.backend.dtos.orderitem.OrderItemResponseDTO;
import com.example.backend.entities.OrderItem;
import com.example.backend.exceptions.OrderItemNotFoundException;
import com.example.backend.mappers.OrderItemMapper;
import com.example.backend.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem orderItem = orderItemMapper.fromRequestDTOToEntity(orderItemRequestDTO);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.fromEntityToResponseDTO(savedOrderItem);
    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId));
        return orderItemMapper.fromEntityToResponseDTO(orderItem);
    }

    @Override
    public List<OrderItemResponseDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems.stream()
                .map(orderItemMapper::fromEntityToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemResponseDTO updateOrderItem(Long orderItemId, OrderItemRequestDTO orderItemRequestDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId));

        orderItemMapper.updateEntityFromRequestDTO(orderItemRequestDTO, orderItem);
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.fromEntityToResponseDTO(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new OrderItemNotFoundException("OrderItem not found with ID: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }
}
