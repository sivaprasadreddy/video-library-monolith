package com.sivalabs.videolibrary.orders.web.dto;

import com.sivalabs.videolibrary.catalog.web.dto.MovieDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private MovieDTO product;

    private int quantity;

    public BigDecimal getSubTotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }
}
