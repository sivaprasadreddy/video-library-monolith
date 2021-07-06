package com.sivalabs.videolibrary.orders.repository;

import com.sivalabs.videolibrary.orders.entity.OrderedProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    Optional<OrderedProduct> findByTmdbId(Long tmdbId);
}
