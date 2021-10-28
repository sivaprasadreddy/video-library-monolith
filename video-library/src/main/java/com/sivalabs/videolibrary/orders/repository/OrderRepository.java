package com.sivalabs.videolibrary.orders.repository;

import com.sivalabs.videolibrary.orders.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(String orderId);

    List<Order> findByStatus(Order.OrderStatus status);

    List<Order> findByCreatedBy(Long userId);
}
