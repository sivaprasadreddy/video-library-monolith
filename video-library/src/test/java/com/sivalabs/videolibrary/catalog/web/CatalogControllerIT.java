package com.sivalabs.videolibrary.catalog.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class CatalogControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldFetchAllCategories() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}
