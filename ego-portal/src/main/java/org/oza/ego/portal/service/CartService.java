package org.oza.ego.portal.service;

import org.oza.ego.base.vo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    /**
     * 添加商品到购物车，并返回当前购物车中的商品集合
     * @param itemId 商品ID
     * @param num 商品数量
     * @param request Request 请求，用于获得 Cookie
     * @param response 通过 response 添加 cookie
     * @return 当前购物车商品集合
     */
    List<CartItem> addToCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

    /**
     * 从 Cookie 中获得购物车中的商品
     * @param request Request 请求，用于获得 Cookie
     * @return 当前购物车商品集合
     */
    List<CartItem> getCart(HttpServletRequest request);

    /**
     * 修改购物车商品的数量
     * @param itemId 商品id
     * @param num 数量
     * @param request
     * @param response
     * @return 商品集合
     */
    List<CartItem> update(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除货物之后，再将货物添加到cookie
     * @param itemId 被删除货物id
     * @param request
     * @param response
     * @return 新的商品集合
     */
    List<CartItem> delete(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
