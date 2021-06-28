package com.sivalabs.videolibrary.web.controller;

import com.sivalabs.videolibrary.web.dto.Cart;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    protected Cart getOrCreateCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        return cart;
    }
}
