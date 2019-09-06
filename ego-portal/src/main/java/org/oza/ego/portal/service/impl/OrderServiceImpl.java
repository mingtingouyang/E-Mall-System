package org.oza.ego.portal.service.impl;

import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.CartItem;
import org.oza.ego.base.vo.OrderDetail;
import org.oza.ego.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${cookie.cart}")
    private String cartCookieName;

    @Value("${order.save.url}")
    private String orderSaveUrl;

    @Override
    public List<CartItem> showOrder(HttpServletRequest request) {

        List<CartItem> cart = new ArrayList<>();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cartCookieName.equals(cookie.getName())) {
                Base64.Decoder decoder = Base64.getDecoder();
                String cartJson = new String(decoder.decode(cookie.getValue()));
                cart = JsonUtils.jsonToList(cartJson, CartItem.class);
                break;
            }
        }
        return cart;
    }

    @Override
    public String save(OrderDetail orderDetail) {
        Map<String, String> params = new HashMap<>();
        params.put("order", JsonUtils.objectToJson(orderDetail));
        String result = null;
        try {
            result = HttpClientUtils.doGet(orderSaveUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
