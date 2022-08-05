package com.sivalabs.videolibrary.orders.domain;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedBy(Long userId);

    Order save(Order order);
}
