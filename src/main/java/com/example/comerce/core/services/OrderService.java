package com.example.comerce.core.services;

import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.entities.Order;
import com.example.comerce.core.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(final UUID orderId) {
        return orderRepository.findById(orderId);
    }

    public Order save(final OrderDTO orderDTO) {
        final Order order = orderDTO.toEntity();
        return orderRepository.save(order);
    }

    public void delete(final UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    public Order update(final UUID orderId, final OrderDTO orderDTO) {
        final Order order = orderDTO.toEntity();
        order.setOrder_id(orderId);
        return orderRepository.save(order);
    }
}
