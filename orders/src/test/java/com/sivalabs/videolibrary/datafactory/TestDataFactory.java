package com.sivalabs.videolibrary.datafactory;

import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.entity.OrderItem;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TestDataFactory {

    public static User createUser(String email, String password) {
        User user = new User();
        user.setName("someuser");
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static Order createOrder(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus(Order.OrderStatus.NEW);
        order.setCustomerName("customer 1");
        order.setCustomerEmail("customer1@gmail.com");
        order.setCreditCardNumber("1111111111111");
        order.setCvv("123");
        order.setDeliveryAddress("Hyderabad");

        Set<OrderItem> items = new HashSet<>();
        items.add(createOrderItem(order));
        order.setItems(items);

        User createdBy = new User();
        createdBy.setId(1L);
        order.setCreatedBy(createdBy.getId());
        return order;
    }

    public static OrderItem createOrderItem(Order order) {
        OrderItem item = new OrderItem();
        item.setProductCode("P001");
        item.setProductName("Some Product");
        item.setProductPrice(BigDecimal.TEN);
        item.setQuantity(1);
        item.setOrder(order);
        return item;
    }
}
