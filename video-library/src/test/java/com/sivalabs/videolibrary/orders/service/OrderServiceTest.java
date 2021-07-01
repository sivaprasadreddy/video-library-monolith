package com.sivalabs.videolibrary.orders.service;

import static com.sivalabs.videolibrary.datafactory.TestDataFactory.createOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import com.sivalabs.videolibrary.common.exception.BadRequestException;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.repository.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;

    @InjectMocks private OrderService orderService;

    String orderId;

    @BeforeEach
    void setUp() {
        orderId = "1234";
    }

    @Test
    void should_throw_exception_when_cancelling_non_existing_order() {
        given(orderRepository.findByOrderId(orderId)).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder(orderId));
    }

    @Test
    void should_throw_exception_when_cancelling_delivered_order() {
        Order order = createOrder(1L);
        order.setStatus(Order.OrderStatus.DELIVERED);
        given(orderRepository.findByOrderId(orderId)).willReturn(Optional.of(order));

        assertThrows(BadRequestException.class, () -> orderService.cancelOrder(orderId));
    }
}
