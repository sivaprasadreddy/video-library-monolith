package com.sivalabs.videolibrary.orders.model;

import com.sivalabs.videolibrary.orders.entity.OrderedProduct;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private OrderedProduct product;
    private int quantity;

    public BigDecimal getSubTotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
