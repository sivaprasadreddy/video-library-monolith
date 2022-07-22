package com.sivalabs.videolibrary.orders.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findByStatus(OrderEntity.OrderStatus status);

    List<OrderEntity> findByCreatedBy(Long userId);
}
