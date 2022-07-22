package com.sivalabs.videolibrary.orders.web;

import com.sivalabs.videolibrary.orders.domain.Cart;
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
}
