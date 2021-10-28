package com.sivalabs.videolibrary.catalog.service;

import static com.sivalabs.videolibrary.common.TestConstants.PROFILE_IT;
import static org.assertj.core.api.Assertions.assertThat;

import com.sivalabs.videolibrary.catalog.repository.ProductRepository;
import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({PROFILE_IT})
class ProductServiceIT extends AbstractIntegrationTest {

    @Autowired private CatalogService catalogService;

    @Autowired private ProductRepository productRepository;

    @Autowired EntityManager entityManager;

    @Test
    void shouldCleanUpData() {
        catalogService.cleanupProductsData();
        assertThat(productRepository.count()).isEqualTo(0);
    }
}
