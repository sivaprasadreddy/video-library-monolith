package com.sivalabs.videolibrary.orders.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct {
    private Long id;
    private String title;
    private Long uuid;
    private BigDecimal price;
}
