package com.sivalabs.videolibrary.orders.web;

import static com.sivalabs.videolibrary.orders.web.CartUtils.CART_KEY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.videolibrary.common.AbstractMvcUnitTest;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import com.sivalabs.videolibrary.customers.domain.Customer;
import com.sivalabs.videolibrary.customers.domain.SecurityUser;
import com.sivalabs.videolibrary.datafactory.TestDataFactory;
import com.sivalabs.videolibrary.orders.domain.Cart;
import com.sivalabs.videolibrary.orders.domain.OrderConfirmationDTO;
import com.sivalabs.videolibrary.orders.domain.OrderService;
import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import com.sivalabs.videolibrary.orders.domain.OrderedProduct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest extends AbstractMvcUnitTest {

    @MockBean private OrderService orderService;

    @Test
    void shouldPlaceOrderSuccessfully() throws Exception {
        var userDetails =
                new SecurityUser(new Customer(1L, "Admin", "admin@gmail.com", "admin1234"));
        var sessionAttrs = new HashMap<String, Object>();
        var cart = new Cart();
        cart.addItem(new OrderedProduct(1L, "Minions: The Rise of Gru", 438148L, BigDecimal.TEN));

        sessionAttrs.put(CART_KEY, cart);

        given(securityService.loginUser()).willReturn(userDetails.getCustomer());
        given(orderService.createOrder(any()))
                .willReturn(new OrderConfirmationDTO("order-id-1234", OrderStatus.NEW));

        mockMvc.perform(
                        post("/orders")
                                .sessionAttrs(sessionAttrs)
                                .with(user(userDetails))
                                .param("customerName", "Siva")
                                .param("customerEmail", "siva@gmail.com")
                                .param("deliveryAddress", "Hyderabad")
                                .param("creditCardNumber", "111111111111")
                                .param("cvv", "123"))
                .andExpect(header().string("Location", "/orders/order-id-1234"));
    }

    @Test
    void shouldRedisplayCheckoutPageIfMandatoryDataMissing() throws Exception {
        var userDetails =
                new SecurityUser(new Customer(1L, "Admin", "admin@gmail.com", "admin1234"));
        var sessionAttrs = new HashMap<String, Object>();
        var cart = new Cart();
        cart.addItem(new OrderedProduct(1L, "Minions: The Rise of Gru", 438148L, BigDecimal.TEN));

        sessionAttrs.put(CART_KEY, cart);

        given(securityService.loginUser()).willReturn(userDetails.getCustomer());
        given(orderService.createOrder(any()))
                .willReturn(new OrderConfirmationDTO("order-id-1234", OrderStatus.NEW));

        mockMvc.perform(
                        post("/orders")
                                .sessionAttrs(sessionAttrs)
                                .with(user(userDetails))
                                .param("customerName", "")
                                .param("customerEmail", "")
                                .param("deliveryAddress", "")
                                .param("creditCardNumber", "")
                                .param("cvv", ""))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(
                        model().attributeHasFieldErrors(
                                        "order",
                                        "customerName",
                                        "customerEmail",
                                        "deliveryAddress",
                                        "creditCardNumber",
                                        "cvv"))
                .andExpect(model().attributeHasFieldErrorCode("order", "customerName", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("order", "customerEmail", "NotBlank"))
                .andExpect(
                        model().attributeHasFieldErrorCode("order", "deliveryAddress", "NotBlank"))
                .andExpect(
                        model().attributeHasFieldErrorCode("order", "creditCardNumber", "NotBlank"))
                .andExpect(model().attributeHasFieldErrorCode("order", "cvv", "NotBlank"))
                .andExpect(view().name("cart"));
    }

    @Test
    void shouldThrowErrorWhenOrderNotFound() throws Exception {
        var orderId = "12345";
        var userDetails =
                new SecurityUser(new Customer(1L, "Admin", "admin@gmail.com", "admin1234"));

        given(orderService.findOrderByOrderId(orderId))
                .willThrow(new ResourceNotFoundException("order not found"));

        mockMvc.perform(get("/orders/{orderId}", orderId).with(user(userDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldNotBeAbleToViewOthersOrders() throws Exception {
        var orderId = "12345";
        var userDetails =
                new SecurityUser(new Customer(1L, "Admin", "admin@gmail.com", "admin1234"));

        var order = TestDataFactory.createOrder(1L);
        order.setCreatedBy(2L);

        given(orderService.findOrderByOrderId(orderId)).willReturn(Optional.of(order));
        given(securityService.loginUser()).willReturn(userDetails.getCustomer());

        mockMvc.perform(get("/orders/{orderId}", orderId).with(user(userDetails)))
                .andExpect(status().isForbidden());
    }
}
