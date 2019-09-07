package org.oza.ego.base.vo;

import org.oza.ego.base.pojo.Order;
import org.oza.ego.base.pojo.OrderItem;
import org.oza.ego.base.pojo.OrderShipping;

import java.util.List;

//用于封装订单的详情
public class OrderDetail extends Order {
    private List<OrderItem> orderItems;

    private OrderShipping orderShipping;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
