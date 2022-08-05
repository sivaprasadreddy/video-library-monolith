package com.sivalabs.videolibrary.orders.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItem implements Serializable {
    private Long id;
    private String productCode;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;

    public BigDecimal getSubTotal() {
        return productPrice.multiply(new BigDecimal(quantity));
    }
}
