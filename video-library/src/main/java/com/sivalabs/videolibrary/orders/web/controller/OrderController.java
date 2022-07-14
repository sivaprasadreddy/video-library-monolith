package com.sivalabs.videolibrary.orders.web.controller;

import static com.sivalabs.videolibrary.orders.web.utils.CartUtils.clearCart;
import static com.sivalabs.videolibrary.orders.web.utils.CartUtils.getOrCreateCart;

import com.sivalabs.videolibrary.common.exception.AccessDeniedException;
import com.sivalabs.videolibrary.common.exception.ResourceNotFoundException;
import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.customers.service.SecurityService;
import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.entity.OrderItem;
import com.sivalabs.videolibrary.orders.model.Cart;
import com.sivalabs.videolibrary.orders.model.CreateOrderRequest;
import com.sivalabs.videolibrary.orders.model.LineItem;
import com.sivalabs.videolibrary.orders.model.OrderConfirmationDTO;
import com.sivalabs.videolibrary.orders.service.OrderService;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Loggable
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    @PostMapping(value = "/cart/checkout")
    @PreAuthorize("isAuthenticated()")
    public String placeOrder(
            @Valid @ModelAttribute("order") CreateOrderRequest order,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        Cart cart = getOrCreateCart(request);
        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("order", order);
            return "cart";
        }

        Order newOrder = new Order();
        newOrder.setCustomerName(order.getCustomerName());
        newOrder.setCustomerEmail(order.getCustomerEmail());
        newOrder.setStatus(Order.OrderStatus.NEW);
        newOrder.setOrderId(UUID.randomUUID().toString());
        newOrder.setCreditCardNumber(order.getCreditCardNumber());
        newOrder.setCvv(order.getCvv());
        newOrder.setDeliveryAddress(order.getDeliveryAddress());
        Set<OrderItem> items = new HashSet<>();
        for (LineItem lineItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setProductCode(String.valueOf(lineItem.getProduct().getTmdbId()));
            orderItem.setProductName(lineItem.getProduct().getTitle());
            orderItem.setProductPrice(lineItem.getProduct().getPrice());
            orderItem.setQuantity(lineItem.getQuantity());
            items.add(orderItem);
        }
        newOrder.setItems(items);
        newOrder.setCreatedBy(securityService.getLoginUserId());

        OrderConfirmationDTO orderConfirmation = orderService.createOrder(newOrder);
        redirectAttributes.addFlashAttribute("orderConfirmation", orderConfirmation);

        clearCart(request);

        return "redirect:/orders/" + orderConfirmation.getOrderId();
    }

    @GetMapping(value = "/orders/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public String viewOrder(@PathVariable(value = "orderId") String orderId, Model model) {
        Order order = orderService.findOrderByOrderId(orderId).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order with Id :" + orderId + " not found");
        }
        if (!Objects.equals(order.getCreatedBy(), securityService.getLoginUserId())) {
            throw new AccessDeniedException();
        }
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping(value = "/orders")
    @PreAuthorize("isAuthenticated()")
    public String getUserOrders(Model model) {
        Long userId = securityService.getLoginUserId();
        List<Order> orders = orderService.findOrdersByUserId(userId);
        model.addAttribute("orders", orders);
        return "user-orders";
    }
}