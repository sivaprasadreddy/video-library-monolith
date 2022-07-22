package com.sivalabs.videolibrary.orders.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductRepository extends JpaRepository<OrderedProductEntity, Long> {
    Optional<OrderedProductEntity> findByTmdbId(Long tmdbId);
}
