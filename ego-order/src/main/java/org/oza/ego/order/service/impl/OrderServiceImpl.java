package org.oza.ego.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.oza.ego.base.mapper.OrderItemMapper;
import org.oza.ego.base.mapper.OrderMapper;
import org.oza.ego.base.mapper.OrderShippingMapper;
import org.oza.ego.base.pojo.Order;
import org.oza.ego.base.pojo.OrderItem;
import org.oza.ego.base.pojo.OrderShipping;
import org.oza.ego.base.utils.IdUtil;
import org.oza.ego.base.vo.OrderDetail;
import org.oza.ego.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderShippingMapper orderShippingMapper;

    @Transactional
    @Override
    public String saveOrderDetail(OrderDetail orderDetail) {
        Date current = new Date();

        //插入订单消息
        orderDetail.setOrderId(String.valueOf(IdUtil.getItemId()));
        orderDetail.setStatus(1);
        orderDetail.setCreateTime(current);
        orderDetail.setUpdateTime(current);
        this.save(orderDetail);

        //插入商品消息
        for (OrderItem orderItem : orderDetail.getOrderItems()) {
            orderItem.setId(String.valueOf(IdUtil.getItemId()));
            orderItem.setOrderId(orderDetail.getOrderId());
            orderItemMapper.insert(orderItem);
        }

        //插入地址信息
        OrderShipping orderShipping = orderDetail.getOrderShipping();
        orderShipping.setOrderId(orderDetail.getOrderId());
        orderShipping.setCreated(current);
        orderShipping.setUpdated(current);
        orderShippingMapper.insert(orderShipping);

        return orderDetail.getOrderId();
    }
}
