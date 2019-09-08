package org.oza.ego.portal.controller;

import org.oza.ego.base.vo.CartItem;
import org.oza.ego.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车，并将商品集合添加到 request 属性中，返回到 cart 视图
     * @param itemId 商品 Id
     * @param num 数量
     * @param request 自动注入的 request
     * @param response 自动注入的 response
     * @return 重定向到购物车视图
     */
    @RequestMapping("/add/{num}/{itemId}")
    public String add(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cart = cartService.addToCart(itemId, num, request, response);
        request.setAttribute("cartList", cart);
        return "redirect:/cart/cart.html";
    }

    /**
     * 查看购物车
     * @param request 自动注入的 request
     * @return 购物车视图
     */
    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request) {
        List<CartItem> cart = cartService.getCart(request);
        request.setAttribute("cartList", cart);
        return "cart";
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return 购物车视图
     */
    @RequestMapping("/update/num/{itemId}/{num}")
    public String update(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cartItems = cartService.update(itemId, num, request, response);
        request.setAttribute("cartList", cartItems);
        return "cart";
    }

    /**
     * 删除购物车商品
     * @param itemId 商品 id
     * @param request
     * @param response
     * @return 购物车视图
     */
    @RequestMapping("/delete/{itemId}")
    public String delete(@PathVariable("itemId") Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cart = cartService.delete(itemId, request, response);
        request.setAttribute("cartList", cart);

        return "cart";
    }
}
