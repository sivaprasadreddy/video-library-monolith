package com.sivalabs.videolibrary.catalog.service;

import com.sivalabs.videolibrary.catalog.entity.Category;
import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.catalog.repository.CategoryRepository;
import com.sivalabs.videolibrary.catalog.repository.ProductRepository;
import com.sivalabs.videolibrary.config.Loggable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@Loggable
public class CatalogService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void cleanupProductsData() {
        productRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products")
    public Page<Product> findProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> findProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategory(categoryId, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products-by-search")
    public Page<Product> searchProducts(String query, Pageable pageable) {
        return productRepository.findByTitleContainingIgnoreCase(query, pageable);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "categories")
    public List<Category> findAllCategories(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "category-by-slug")
    public Optional<Category> findCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(
            value = {"products", "products-by-search"},
            allEntries = true)
    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @CacheEvict(
            value = {"categories", "category-by-slug"},
            allEntries = true)
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
