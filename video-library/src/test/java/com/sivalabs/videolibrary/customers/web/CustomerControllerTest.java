package com.sivalabs.videolibrary.customers.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.videolibrary.common.AbstractMvcUnitTest;
import com.sivalabs.videolibrary.common.exception.ResourceAlreadyExistsException;
import com.sivalabs.videolibrary.customers.domain.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest extends AbstractMvcUnitTest {

    @MockBean private CustomerService customerService;

    @Test
    void shouldShowRegistrationFormPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void shouldRedisplayRegistrationFormPageWhenSubmittedInvalidData() throws Exception {
        given(customerService.createUser(any())).willAnswer(ans -> ans.getArgument(0));

        mockMvc.perform(
                        post("/registration")
                                .param("name", "")
                                .param("email", "")
                                .param("password", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("customer", "name", "email", "password"))
                .andExpect(model().attributeHasFieldErrorCode("customer", "name", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("customer", "email", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("customer", "password", "NotBlank"))
                .andExpect(view().name("registration"));
    }

    @Test
    void shouldRedisplayRegistrationFormPageWhenEmailAlreadyExists() throws Exception {
        given(customerService.createUser(any()))
                .willThrow(new ResourceAlreadyExistsException("customer already exists"));
        mockMvc.perform(
                        post("/registration")
                                .param("name", "dummy")
                                .param("email", "dummy@mail.com")
                                .param("password", "admin1234"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("customer", "email"))
                .andExpect(model().attributeHasFieldErrorCode("customer", "email", "email.exists"))
                .andExpect(view().name("registration"));
    }
}
