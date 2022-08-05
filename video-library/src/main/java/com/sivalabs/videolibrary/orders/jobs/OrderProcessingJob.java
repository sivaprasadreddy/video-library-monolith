package com.sivalabs.videolibrary.orders.jobs;

import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.orders.domain.Order;
import com.sivalabs.videolibrary.orders.domain.OrderService;
import com.sivalabs.videolibrary.orders.domain.OrderStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Loggable
public class OrderProcessingJob {

    private final OrderService orderService;

    @Scheduled(fixedDelay = 2 * 60 * 1000) // every 2 minutes
    void processOrders() {
        List<Order> orders = orderService.findOrdersByStatus(OrderStatus.NEW);
        if (orders.isEmpty()) {
            log.info("No new orders to be processed");
            return;
        }
        for (Order order : orders) {
            log.info("Processing order {} ", order.getOrderId());
            order.setStatus(OrderStatus.DELIVERED);
            orderService.updateOrder(order);
            log.info("Order {} is delivered", order.getOrderId());
        }
    }
}
