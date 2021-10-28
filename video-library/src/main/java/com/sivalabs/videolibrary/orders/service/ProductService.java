package com.sivalabs.videolibrary.orders.service;

import com.sivalabs.videolibrary.orders.entity.OrderedProduct;
import com.sivalabs.videolibrary.orders.repository.OrderedProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final OrderedProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<OrderedProduct> findProductByCode(Long tmdbId) {
        return productRepository.findByTmdbId(tmdbId);
    }
}
