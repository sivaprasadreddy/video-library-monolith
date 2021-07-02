package com.sivalabs.videolibrary.catalog.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.videolibrary.catalog.service.CatalogService;
import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductControllerIT extends AbstractIntegrationTest {

    @Autowired private CatalogService catalogService;

    @BeforeEach
    void setUp() {}

    @Test
    void shouldFetchAllCategories() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}
