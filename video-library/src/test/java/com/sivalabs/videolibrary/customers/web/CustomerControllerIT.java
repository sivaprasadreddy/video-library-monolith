package com.sivalabs.videolibrary.customers.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class CustomerControllerIT extends AbstractIntegrationTest {
    @Test
    void shouldRegisterSuccessfully() throws Exception {
        mockMvc.perform(
                        post("/registration")
                                .param("name", "dummy")
                                .param("email", "dummy@mail.com")
                                .param("password", "admin1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("msg", "Registration is successful"))
                .andExpect(header().string("Location", "/login"));
    }
}
