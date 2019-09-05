package org.oza.ego.portal.service;

import org.oza.ego.base.vo.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    /**
     * 从cookie 中获取购物车物品
     * @param request
     * @return
     */
    List<CartItem> showOrder(HttpServletRequest request);
}
