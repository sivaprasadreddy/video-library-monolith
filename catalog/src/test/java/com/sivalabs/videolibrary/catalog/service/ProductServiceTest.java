package com.sivalabs.videolibrary.catalog.service;

import static com.sivalabs.videolibrary.datafactory.TestDataFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.catalog.repository.CategoryRepository;
import com.sivalabs.videolibrary.catalog.repository.ProductRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock private ProductRepository productRepository;
    @Mock private CategoryRepository categoryRepository;
    @InjectMocks private CatalogService catalogService;

    @Test
    void should_get_all_products() {
        Pageable pageable = PageRequest.of(0, 10);
        given(productRepository.findAll(pageable)).willReturn(new PageImpl<>(new ArrayList<>()));

        Page<Product> products = catalogService.findProducts(pageable);

        assertThat(products).isNotNull();
    }

    @Test
    void shout_save_new_product() {
        Product product = createProduct("abcd", "genre-1", "genre-2");
        given(productRepository.save(any(Product.class)))
                .willAnswer(answer -> answer.getArgument(0));

        Product newProduct = catalogService.createProduct(product);

        assertThat(newProduct).isNotNull();
    }
}
