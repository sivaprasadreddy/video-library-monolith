package com.sivalabs.videolibrary.orders.web.controller;

import com.sivalabs.videolibrary.catalog.service.MovieService;
import com.sivalabs.videolibrary.catalog.web.dto.MovieDTO;
import com.sivalabs.videolibrary.catalog.web.mappers.MovieDTOMapper;
import com.sivalabs.videolibrary.config.Loggable;
import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.customers.service.SecurityService;
import com.sivalabs.videolibrary.orders.entity.Order;
import com.sivalabs.videolibrary.orders.entity.OrderItem;
import com.sivalabs.videolibrary.orders.model.OrderConfirmationDTO;
import com.sivalabs.videolibrary.orders.service.OrderService;
import com.sivalabs.videolibrary.orders.web.dto.Cart;
import com.sivalabs.videolibrary.orders.web.dto.LineItem;
import com.sivalabs.videolibrary.orders.web.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Loggable
@Slf4j
public class CartController {

    private final MovieService movieService;

    private final SecurityService securityService;

    private final OrderService orderService;

    private final MovieDTOMapper movieDTOMapper;

    @GetMapping(value = "/cart")
    public String showCart(HttpServletRequest request, Model model) {
        Cart cart = getOrCreateCart(request);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping(value = "/cart/checkout")
    public String checkout(
            @Valid @ModelAttribute("order") OrderDTO order,
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
        User user = securityService.loginUser();
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
            orderItem.setProductCode("" + lineItem.getProduct().getTmdbId());
            orderItem.setProductName(lineItem.getProduct().getTitle());
            orderItem.setProductPrice(lineItem.getProduct().getPrice());
            orderItem.setQuantity(lineItem.getQuantity());
            items.add(orderItem);
        }
        newOrder.setItems(items);
        newOrder.setCreatedBy(user);

        OrderConfirmationDTO orderConfirmation = orderService.createOrder(newOrder);
        redirectAttributes.addFlashAttribute("orderConfirmation", orderConfirmation);

        request.getSession().removeAttribute("CART_KEY");

        return "redirect:/orders/" + orderConfirmation.getOrderId();
    }

    @GetMapping(value = "/orders/{orderId}")
    public String viewOrder(@PathVariable(value = "orderId") String orderId, Model model) {
        Order order = orderService.findOrderByOrderId(orderId).orElse(null);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping(value = "/cart/items/count")
    @ResponseBody
    public Map<String, Object> getCartItemCount(HttpServletRequest request) {
        Cart cart = getOrCreateCart(request);
        int itemCount = cart.getItemCount();
        Map<String, Object> map = new HashMap<>();
        map.put("count", itemCount);
        return map;
    }

    @PostMapping(value = "/cart/items")
    @ResponseBody
    public Cart addToCart(@RequestBody MovieDTO product, HttpServletRequest request) {
        log.info("Add tmdbId: {} to cart", product.getTmdbId());
        Cart cart = getOrCreateCart(request);
        MovieDTO p =
                movieService
                        .findMovieByTmdbId(product.getTmdbId())
                        .map(movieDTOMapper::map)
                        .orElse(null);
        log.info("Adding product: {}", p.getTmdbId());
        cart.addItem(p);
        return cart;
    }

    @PutMapping(value = "/cart/items")
    @ResponseBody
    public Cart updateCartItem(@RequestBody LineItem item, HttpServletRequest request) {
        log.info(
                "Update cart line item tmdbId: {}, quantity: {} ",
                item.getProduct().getTmdbId(),
                item.getQuantity());
        Cart cart = getOrCreateCart(request);
        if (item.getQuantity() <= 0) {
            Long tmdbId = item.getProduct().getTmdbId();
            cart.removeItem(tmdbId);
        } else {
            cart.updateItemQuantity(item.getProduct(), item.getQuantity());
        }
        return cart;
    }

    @DeleteMapping(value = "/cart/items/{tmdbId}")
    @ResponseBody
    public Cart removeCartItem(@PathVariable("tmdbId") Long tmdbId, HttpServletRequest request) {
        log.info("Remove cart line item tmdbId: {}", tmdbId);
        Cart cart = getOrCreateCart(request);
        cart.removeItem(tmdbId);
        return cart;
    }

    @DeleteMapping(value = "/cart")
    @ResponseBody
    public Cart clearCart(HttpServletRequest request) {
        log.info("Clear cart");
        Cart cart = getOrCreateCart(request);
        cart.setItems(new ArrayList<>(0));
        return cart;
    }

    Cart getOrCreateCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        return cart;
    }
}
