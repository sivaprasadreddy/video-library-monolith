package com.sivalabs.videolibrary.orders.adapter;

import com.sivalabs.videolibrary.orders.domain.OrderedProduct;
import com.sivalabs.videolibrary.orders.domain.OrderedProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderedProductRepositoryImpl implements OrderedProductRepository {
    private final JpaOrderedProductRepository jpaOrderedProductRepository;
    private final OrderedProductMapper orderedProductMapper;

    @Override
    public Optional<OrderedProduct> findByUuid(Long uuid) {
        return jpaOrderedProductRepository.findByUuid(uuid).map(orderedProductMapper::map);
    }
}
