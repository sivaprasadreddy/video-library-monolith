package com.sivalabs.videolibrary.catalog.adapter;

import com.sivalabs.videolibrary.catalog.domain.Product;
import com.sivalabs.videolibrary.catalog.domain.ProductRepository;
import com.sivalabs.videolibrary.common.models.PagedResult;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private static final int PAGE_SIZE = 24;
    private final JpaProductRepository jpaProductRepository;
    private final ProductMapper productMapper;

    private Pageable getDefaultPageRequest(int pageNo) {
        int page = pageNo > 0 ? pageNo - 1 : 0;
        Sort sort = Sort.by("releaseDate").descending().and(Sort.by("title"));
        return PageRequest.of(page, PAGE_SIZE, sort);
    }

    @Override
    public PagedResult<Product> findAll(int pageNo) {
        Pageable pageable = getDefaultPageRequest(pageNo);
        Page<Product> productPage = jpaProductRepository.findAll(pageable).map(productMapper::map);
        return new PagedResult<>(productPage);
    }

    @Override
    public PagedResult<Product> findByCategory(Long categoryId, int pageNo) {
        Pageable pageable = getDefaultPageRequest(pageNo);
        Page<Product> productPage =
                jpaProductRepository.findByCategory(categoryId, pageable).map(productMapper::map);
        return new PagedResult<>(productPage);
    }

    @Override
    public PagedResult<Product> findByTitleContainingIgnoreCase(String query, int pageNo) {
        Pageable pageable = getDefaultPageRequest(pageNo);
        Page<Product> productPage =
                jpaProductRepository
                        .findByTitleContainingIgnoreCase(query, pageable)
                        .map(productMapper::map);
        return new PagedResult<>(productPage);
    }

    @Override
    public void deleteAllInBatch() {
        jpaProductRepository.deleteAllInBatch();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaProductRepository.findById(id).map(productMapper::map);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.mapToEntity(product);
        var savedProduct = jpaProductRepository.save(entity);
        return productMapper.map(savedProduct);
    }

    @Override
    public Long count() {
        return jpaProductRepository.count();
    }
}
