package com.sivalabs.videolibrary.orders.web;

import com.sivalabs.videolibrary.orders.domain.Cart;
import com.sivalabs.videolibrary.orders.domain.LineItem;
import com.sivalabs.videolibrary.orders.domain.OrderedProduct;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class CartUtils {
    public static final String CART_KEY = "CART_KEY";

    public static Cart getOrCreateCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_KEY);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    public static Cart clearCart(HttpServletRequest request) {
        Cart cart = getOrCreateCart(request);
        cart.setItems(new ArrayList<>(0));
        return cart;
    }

    public static Cart addItem(HttpServletRequest request, OrderedProduct item) {
        Cart cart = getOrCreateCart(request);
        cart.addItem(item);
        return cart;
    }

    public static Cart updateCartItem(HttpServletRequest request, LineItem item) {
        Cart cart = getOrCreateCart(request);
        if (item.getQuantity() <= 0) {
            Long uuid = item.getProduct().getUuid();
            cart.removeItem(uuid);
        } else {
            cart.updateItemQuantity(item.getProduct(), item.getQuantity());
        }
        return cart;
    }

    public static Cart removeCartItem(HttpServletRequest request, Long uuid) {
        Cart cart = getOrCreateCart(request);
        cart.removeItem(uuid);
        return cart;
    }
}
