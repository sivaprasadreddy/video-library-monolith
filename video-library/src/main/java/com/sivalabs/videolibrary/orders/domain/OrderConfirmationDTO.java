package com.sivalabs.videolibrary.orders.domain;

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
    private OrderEntity.OrderStatus orderStatus;
}
