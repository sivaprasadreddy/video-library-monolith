package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.orders.domain.Order;
import com.sivalabs.videolibrary.orders.domain.OrderRepository;
import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final JpaOrderRepository jpaOrderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        return jpaOrderRepository.findByOrderId(orderId).map(orderMapper::map);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return jpaOrderRepository.findByStatus(status).stream().map(orderMapper::map).toList();
    }

    @Override
    public List<Order> findByCreatedBy(Long userId) {
        return jpaOrderRepository.findByCreatedBy(userId).stream().map(orderMapper::map).toList();
    }

    @Override
    public Order save(Order order) {
        var entity = orderMapper.mapToEntity(order);
        var savedEntity = jpaOrderRepository.save(entity);
        return orderMapper.map(savedEntity);
    }
}
