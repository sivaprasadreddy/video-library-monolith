package com.sivalabs.videolibrary.orders.web;

import static com.sivalabs.videolibrary.orders.web.CartUtils.getOrCreateCart;

import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.orders.domain.Cart;
import com.sivalabs.videolibrary.orders.domain.CreateOrderRequest;
import com.sivalabs.videolibrary.orders.domain.LineItem;
import com.sivalabs.videolibrary.orders.domain.OrderedProduct;
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
        model.addAttribute("order", new CreateOrderRequest());
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
    public Cart addToCart(@RequestBody OrderedProduct product, HttpServletRequest request) {
        log.info("Add uuid: {} to cart", product.getUuid());
        OrderedProduct item = productService.findProductByCode(product.getUuid()).orElseThrow();
        log.info("Adding product: {}", item.getUuid());
        return CartUtils.addItem(request, item);
    }

    @PutMapping(value = "/cart/items")
    @ResponseBody
    public Cart updateCartItem(@RequestBody LineItem item, HttpServletRequest request) {
        log.info(
                "Update cart line item uuid: {}, quantity: {} ",
                item.getProduct().getUuid(),
                item.getQuantity());
        return CartUtils.updateCartItem(request, item);
    }

    @DeleteMapping(value = "/cart/items/{uuid}")
    @ResponseBody
    public Cart removeCartItem(@PathVariable("uuid") Long uuid, HttpServletRequest request) {
        log.info("Remove cart line item uuid: {}", uuid);
        return CartUtils.removeCartItem(request, uuid);
    }
}
