package com.sivalabs.videolibrary.orders.adapter;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, Long> {
    Optional<OrderedProductEntity> findByUuid(Long uuid);
}
