package com.sivalabs.videolibrary.catalog.domain;

import java.util.List;
import java.util.Optional;

import com.sivalabs.videolibrary.common.models.PagedResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CatalogService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void deleteAllProducts() {
        productRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public PagedResult<Product> findProducts(int pageNo) {
        return productRepository.findAll(pageNo);
    }

    @Transactional(readOnly = true)
    public PagedResult<Product> findProductsByCategory(Long categoryId, int pageNo) {
        return productRepository.findByCategory(categoryId, pageNo);
    }

    @Transactional(readOnly = true)
    public PagedResult<Product> searchProducts(String query, int pageNo) {
        return productRepository.findByTitleContainingIgnoreCase(query, pageNo);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Long findProductsCount() {
        return productRepository.count();
    }

    // ========= Category ============== //
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Category> findCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

}
