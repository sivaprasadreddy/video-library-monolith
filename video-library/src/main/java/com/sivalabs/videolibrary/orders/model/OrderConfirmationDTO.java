package com.sivalabs.videolibrary.orders.model;

import com.sivalabs.videolibrary.orders.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmationDTO {

    private String orderId;

    private Order.OrderStatus orderStatus;
}
