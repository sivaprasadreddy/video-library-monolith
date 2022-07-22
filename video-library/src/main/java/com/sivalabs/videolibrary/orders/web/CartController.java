package com.sivalabs.videolibrary.orders.web;

import static com.sivalabs.videolibrary.orders.web.CartUtils.getOrCreateCart;

import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.orders.domain.Cart;
import com.sivalabs.videolibrary.orders.domain.LineItem;
import com.sivalabs.videolibrary.orders.domain.OrderedProductEntity;
import com.sivalabs.videolibrary.orders.domain.ProductService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Loggable
@Slf4j
public class CartController {

    private final ProductService productService;

    @GetMapping(value = "/cart")
    public String showCart(HttpServletRequest request, Model model) {
        Cart cart = getOrCreateCart(request);
        model.addAttribute("cart", cart);
        return "cart";
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
    public Cart addToCart(@RequestBody OrderedProductEntity product, HttpServletRequest request) {
        log.info("Add tmdbId: {} to cart", product.getTmdbId());
        Cart cart = getOrCreateCart(request);
        OrderedProductEntity item =
                productService.findProductByCode(product.getTmdbId()).orElse(null);
        log.info("Adding product: {}", item.getTmdbId());
        cart.addItem(item);
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
        return CartUtils.clearCart(request);
    }
}
