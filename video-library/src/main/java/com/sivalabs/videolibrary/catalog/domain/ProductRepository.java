package com.sivalabs.videolibrary.catalog.domain;

import com.sivalabs.videolibrary.common.models.PagedResult;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    PagedResult<Product> findAll(int pageNo);

    PagedResult<Product> findByCategory(Long categoryId, int pageNo);

    PagedResult<Product> findByTitleContainingIgnoreCase(String query, int pageNo);

    void deleteAllInBatch();

    Product save(Product product);

    Long count();
}
