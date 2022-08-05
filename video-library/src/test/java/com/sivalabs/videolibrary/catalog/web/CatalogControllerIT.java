package com.sivalabs.videolibrary.catalog.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CatalogControllerIT extends AbstractIntegrationTest {

    @ParameterizedTest
    @CsvSource({"1", "2"})
    void shouldShowProductsByPage(int pageNo) throws Exception {
        mockMvc.perform(get("/?page={page}", pageNo))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("paginationPrefix"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("productsData"))
                .andExpect(model().attributeExists("categories"));
    }

    @ParameterizedTest
    @CsvSource({"crime", "pulp"})
    void shouldSearchProductsByQuery(String query) throws Exception {
        mockMvc.perform(get("/?query={query}", query))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("paginationPrefix"))
                .andExpect(model().attributeExists("header"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("productsData"))
                .andExpect(model().attributeExists("categories"));
    }

    @ParameterizedTest
    @CsvSource({"1", "2"})
    void shouldShowProductById(int id) throws Exception {
        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("categories"));
    }

    @ParameterizedTest
    @CsvSource({"action", "comedy"})
    void shouldShowProductsByCategory(String slug) throws Exception {
        mockMvc.perform(get("/category/{slug}", slug))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("paginationPrefix"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("productsData"))
                .andExpect(model().attributeExists("categories"));
    }
}
