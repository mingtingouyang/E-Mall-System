package org.oza.ego.portal.controller;

import org.oza.ego.base.vo.CartItem;
import org.oza.ego.base.vo.OrderDetail;
import org.oza.ego.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${cookie.cart}")
    private String cartCookieName;

    /**
     * 订单页面显示购物车商品详情
     * @param request 请求对象，获取cookie
     * @return 订单视图
     */
    @RequestMapping("/order-cart")
    public String showOrder(HttpServletRequest request) {
        List<CartItem> cart = orderService.showOrder(request);
        request.setAttribute("cartList", cart);
        return "order-cart";
    }

    @PostMapping("/create")
    public String save(OrderDetail order, Model model, HttpServletResponse response) {
        String orderId = null;
        try {
            orderId = orderService.save(order);
            //成功添加订单后
            if (null != orderId) {
                model.addAttribute("orderId", orderId);
                model.addAttribute("payment", order.getPayment());
                //订单的预计送达时间为三天后
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, 3);
                Date date = calendar.getTime();
                //格式化
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/E");
                model.addAttribute("date", dateFormat.format(date));
                //添加完订单后清除cookie：设置同样的cookie，age为0
                Cookie cookie = new Cookie(cartCookieName, "");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果出现异常，返回错误视图
        model.addAttribute("message", orderId);
        return "error/exception";
    }

}
