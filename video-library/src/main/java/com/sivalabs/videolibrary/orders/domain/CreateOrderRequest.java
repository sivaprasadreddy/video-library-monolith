package com.sivalabs.videolibrary.orders.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateOrderRequest {

    @NotBlank(message = "Customer Name is required")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email
    private String customerEmail;

    @NotBlank(message = "DeliveryAddress is required")
    private String deliveryAddress;

    @NotBlank(message = "Credit Card Number is required")
    private String creditCardNumber;

    @NotBlank(message = "CVV is required")
    private String cvv;
}
