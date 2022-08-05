package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.orders.domain.OrderedProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderedProductMapper {

    public OrderedProduct map(OrderedProductEntity entity) {
        return new OrderedProduct(
                entity.getId(), entity.getTitle(), entity.getUuid(), entity.getPrice());
    }
}
