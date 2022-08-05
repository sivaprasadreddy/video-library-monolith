package com.sivalabs.videolibrary.orders.domain;

import java.util.Optional;

public interface OrderedProductRepository {
    Optional<OrderedProduct> findByUuid(Long uuid);
}
