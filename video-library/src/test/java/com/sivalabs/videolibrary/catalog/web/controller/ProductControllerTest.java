package com.sivalabs.videolibrary.catalog.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.catalog.mappers.ProductDTOMapper;
import com.sivalabs.videolibrary.catalog.service.CatalogService;
import com.sivalabs.videolibrary.common.AbstractMvcUnitTest;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@WebMvcTest(controllers = CatalogController.class)
class ProductControllerTest extends AbstractMvcUnitTest {

    @MockBean private CatalogService catalogService;

    @SpyBean private ProductDTOMapper productDTOMapper;

    @Test
    void shouldShowHomePage() throws Exception {
        Page<Product> page = new PageImpl<>(new ArrayList<>());
        given(catalogService.findProducts(any(Pageable.class))).willReturn(page);
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"));
    }
}
