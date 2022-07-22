package com.sivalabs.videolibrary.catalog.domain;

import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CatalogService {
    private static final int PAGE_SIZE = 24;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void cleanupProductsData() {
        productRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public Optional<ProductEntity> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products")
    public Page<ProductEntity> findProducts(int pageNo) {
        Sort sort = Sort.by("releaseDate").descending().and(Sort.by("title"));
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ProductEntity> findProductsByCategory(Long categoryId, int pageNo) {
        Sort sort = Sort.by("releaseDate").descending().and(Sort.by("title"));
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);
        return productRepository.findByCategory(categoryId, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products-by-search")
    public Page<ProductEntity> searchProducts(String query, int pageNo) {
        Sort sort = Sort.by("releaseDate").descending().and(Sort.by("title"));
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);
        return productRepository.findByTitleContainingIgnoreCase(query, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "categories")
    public List<CategoryEntity> findAllCategories() {
        Sort sort = Sort.by(ASC, "name");
        return categoryRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "category-by-slug")
    public Optional<CategoryEntity> findCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @CacheEvict(
            value = {"products", "products-by-search"},
            allEntries = true)
    public List<ProductEntity> createProducts(List<ProductEntity> products) {
        return productRepository.saveAll(products);
    }

    @CacheEvict(
            value = {"categories", "category-by-slug"},
            allEntries = true)
    public CategoryEntity saveCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }
}
