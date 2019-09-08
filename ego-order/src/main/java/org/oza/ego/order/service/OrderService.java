package org.oza.ego.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.oza.ego.base.pojo.Order;
import org.oza.ego.base.vo.OrderDetail;

public interface OrderService extends IService<Order> {

    /**
     * 将订单详情写入数据库，同时写入三张表，有事务管理
     * @param orderDetail 封装的对象，里面组合了商品集合和收件人地址信息
     * @return 订单编号
     */
    String saveOrderDetail(OrderDetail orderDetail);
}
