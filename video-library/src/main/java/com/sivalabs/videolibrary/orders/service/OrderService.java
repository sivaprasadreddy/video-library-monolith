package com.sivalabs.videolibrary.orders.service;

import com.sivalabs.videolibrary.common.exception.BadRequestException;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import com.sivalabs.videolibrary.config.Loggable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.model.OrderConfirmationDTO;
import com.sivalabs.videolibrary.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@Loggable
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderConfirmationDTO createOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(Order.OrderStatus.NEW);
        order.getItems().forEach(lineItem -> lineItem.setOrder(order));
        Order savedOrder = this.orderRepository.save(order);
        log.info("Created Order ID=" + savedOrder.getId() + ", ref_num=" + savedOrder.getOrderId());
        return new OrderConfirmationDTO(savedOrder.getOrderId(), savedOrder.getStatus());
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderByOrderId(String orderId) {
        return this.orderRepository.findByOrderId(orderId);
    }

    public void cancelOrder(String orderId) {
        log.info("Cancel order with OrderId: {}", orderId);
        Order order = findOrderByOrderId(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order with id: " + orderId + " is not found");
        }

        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new BadRequestException("Order is already delivered");
        }
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}
