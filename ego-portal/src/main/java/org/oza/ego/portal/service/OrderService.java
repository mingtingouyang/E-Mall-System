package org.oza.ego.portal.service;

import org.oza.ego.base.vo.CartItem;
import org.oza.ego.base.vo.OrderDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    /**
     * 从cookie 中获取购物车物品
     * @param request
     * @return
     */
    List<CartItem> showOrder(HttpServletRequest request);

    /**
     * 调用接口保存订单
     * @param orderDetail 订单详情对象
     * @return 订单编号
     */
    String save(OrderDetail orderDetail);
}
