package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.orders.domain.Order;
import com.sivalabs.videolibrary.orders.domain.OrderItem;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order map(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getId());
        order.setOrderId(entity.getOrderId());
        order.setCustomerName(entity.getCustomerName());
        order.setCustomerEmail(entity.getCustomerEmail());
        order.setDeliveryAddress(entity.getDeliveryAddress());
        order.setCreditCardNumber(entity.getCreditCardNumber());
        order.setCvv(entity.getCvv());
        order.setCreatedBy(entity.getCreatedBy());
        order.setStatus(entity.getStatus());
        Set<OrderItem> orderItems =
                entity.getItems().stream().map(this::mapToOrderItem).collect(Collectors.toSet());
        order.setItems(orderItems);
        return order;
    }

    public OrderEntity mapToEntity(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setOrderId(order.getOrderId());
        entity.setCustomerName(order.getCustomerName());
        entity.setCustomerEmail(order.getCustomerEmail());
        entity.setDeliveryAddress(order.getDeliveryAddress());
        entity.setCreditCardNumber(order.getCreditCardNumber());
        entity.setCvv(order.getCvv());
        entity.setCreatedBy(order.getCreatedBy());
        entity.setStatus(order.getStatus());

        Set<OrderItemEntity> orderItemEntities =
                order.getItems().stream()
                        .map(this::mapToOrderItemEntity)
                        .collect(Collectors.toSet());
        orderItemEntities.forEach(item -> item.setOrder(entity));
        entity.setItems(orderItemEntities);

        return entity;
    }

    public OrderItem mapToOrderItem(OrderItemEntity entity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(entity.getId());
        orderItem.setQuantity(entity.getQuantity());
        orderItem.setProductCode(entity.getProductCode());
        orderItem.setProductName(entity.getProductName());
        orderItem.setProductPrice(entity.getProductPrice());
        return orderItem;
    }

    public OrderItemEntity mapToOrderItemEntity(OrderItem orderItem) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(orderItem.getId());
        entity.setQuantity(orderItem.getQuantity());
        entity.setProductPrice(orderItem.getProductPrice());
        entity.setProductCode(orderItem.getProductCode());
        entity.setProductName(orderItem.getProductName());
        return entity;
    }
}
