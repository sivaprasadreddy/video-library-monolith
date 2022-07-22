package com.sivalabs.videolibrary.orders.domain;

import com.sivalabs.videolibrary.common.exception.BadRequestException;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderConfirmationDTO createOrder(OrderEntity order) {
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(OrderEntity.OrderStatus.NEW);
        order.getItems().forEach(lineItem -> lineItem.setOrder(order));
        OrderEntity savedOrder = this.orderRepository.save(order);
        log.info("Created Order ID=" + savedOrder.getId() + ", ref_num=" + savedOrder.getOrderId());
        return new OrderConfirmationDTO(savedOrder.getOrderId(), savedOrder.getStatus());
    }

    public Optional<OrderEntity> findOrderByOrderId(String orderId) {
        return this.orderRepository.findByOrderId(orderId);
    }

    public void cancelOrder(String orderId) {
        log.info("Cancel order with OrderId: {}", orderId);
        OrderEntity order = findOrderByOrderId(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order with id: " + orderId + " is not found");
        }

        if (order.getStatus() == OrderEntity.OrderStatus.DELIVERED) {
            throw new BadRequestException("Order is already delivered");
        }
        order.setStatus(OrderEntity.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<OrderEntity> findOrdersByStatus(OrderEntity.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public void updateOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    public List<OrderEntity> findOrdersByUserId(Long userId) {
        return this.orderRepository.findByCreatedBy(userId);
    }
}
