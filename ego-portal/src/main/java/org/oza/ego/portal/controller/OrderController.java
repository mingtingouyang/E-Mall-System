package org.oza.ego.portal.controller;

import org.oza.ego.base.vo.CartItem;
import org.oza.ego.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrder(HttpServletRequest request) {
        List<CartItem> cart = orderService.showOrder(request);
        request.setAttribute("cartList", cart);
        return "order-cart";
    }
}
