package org.oza.ego.order.controller;

import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.OrderDetail;
import org.oza.ego.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 将其他服务传来的订单详情json字符串转化成orderDetail再存储进数据库
     * @param order json字符串
     * @return 订单编号
     */
    @RequestMapping("/create")
    public String save(String order) {
        System.out.println(order);
        OrderDetail orderDetail = null;
        try{
            orderDetail = JsonUtils.jsonToPojo(order, OrderDetail.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != orderDetail)
            return  orderService.saveOrderDetail(orderDetail);

        return null;
    }
}
