package org.oza.ego.order.controller;

import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.OrderDetail;
import org.oza.ego.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 将其他服务传来的订单详情json字符串转化成orderDetail再存储进数据库
     * @param orderDetail 订单详情，远程调用该服务传入json
     * @return 订单编号
     */
    @RequestMapping("/create")
    public EgoResult save(@RequestBody OrderDetail orderDetail) {
        try{
            String orderId = orderService.saveOrderDetail(orderDetail);
            return EgoResult.ok(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return new EgoResult(400, "保存订单失败");
        }
    }
}
