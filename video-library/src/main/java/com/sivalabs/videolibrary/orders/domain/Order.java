package com.sivalabs.videolibrary.orders.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order implements Serializable {

    private Long id;
    private String orderId;
    private String customerName;
    private String customerEmail;
    private String deliveryAddress;
    private String creditCardNumber;
    private String cvv;
    private Set<OrderItem> items;
    private OrderStatus status;
    private Long createdBy;

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0.0");
        for (OrderItem orderItem : items) {
            amount = amount.add(orderItem.getSubTotal());
        }
        return amount;
    }
}
