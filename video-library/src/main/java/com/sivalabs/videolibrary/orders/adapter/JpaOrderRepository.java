package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findByStatus(OrderStatus status);

    List<OrderEntity> findByCreatedBy(Long userId);
}
