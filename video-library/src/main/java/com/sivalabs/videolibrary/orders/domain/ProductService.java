package com.sivalabs.videolibrary.orders.domain;

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
    public Optional<OrderedProductEntity> findProductByCode(Long tmdbId) {
        return productRepository.findByTmdbId(tmdbId);
    }
}
