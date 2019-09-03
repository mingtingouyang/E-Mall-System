package org.oza.ego.portal.service.impl;

import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.CartItem;
import org.oza.ego.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Value("${cookie.cart}")
    private String cartCookieName;

    @Value("${rest.baseUrl}")
    private String baseUrl;

    @Value("${rest.item}")
    private String itemUrl;

    @Override
    public List<CartItem> addToCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //先获得当前购物车内的商品
        List<CartItem> cart = getCart(request);

        //配置个flag，标识是否完成添加
        boolean isAdded = false;

        //判断当前待添加的物品是不是存在于购物车内，若是，只添加数量
        for (CartItem c : cart) {
            //包装类对象不能直接比较
            if (c.getId().longValue() == itemId.longValue()) {
                c.setNum(c.getNum() + num);
                isAdded = true;
                break;
            }
        }
        //判断前面是否完成添加
        if (!isAdded) {
            //从接口中查出当前商品的信息
            String itemJson = HttpClientUtils.doGet(baseUrl + itemUrl + itemId, null);
            if (null != itemJson) {
                Item item = JsonUtils.jsonToPojo(itemJson, Item.class);
                if (null != item) {
                    //将商品信息添加到 CartItem 中
                    CartItem cartItem = new CartItem();
                    cartItem.setId(itemId);
                    cartItem.setNum(num);
                    cartItem.setPrice(item.getPrice());
                    cartItem.setTitle(item.getTitle());
                    cartItem.setImage(item.getImage());
                    //添加至购物车
                    cart.add(cartItem);
                }
            }
        }
        //添加购物车到cookie
        addCookie(cart, response);

        return cart;
    }

    @Override
    public List<CartItem> getCart(HttpServletRequest request) {
        List<CartItem> cart = new ArrayList<>();

        //获取所有的Cookie
        Cookie[] cookies = request.getCookies();

        //避免空指针，没有cookie 则返回空购物车
        if (null != cookies) {
            //遍历查找购物车的 cookie
            for (Cookie cookie : cookies) {
                if (cartCookieName.equals(cookie.getName())) {
                    //使用 base64 解码 cookie 内容
                    Base64.Decoder decoder = Base64.getDecoder();
                    byte[] jsonBytes = decoder.decode(cookie.getValue());
                    cart = JsonUtils.jsonToList(new String(jsonBytes), CartItem.class);
                    break;
                }
            }
        }

        return cart;
    }

    @Override
    public List<CartItem> update(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        //获取购物车列表
        List<CartItem> cart = getCart(request);
        //循环判断，如果id相同则修改数目
        for (CartItem cartItem : cart) {
            if (itemId.longValue() == cartItem.getId().longValue()) {
                cartItem.setNum(num);
                break;
            }
        }
        //将新的购物车添加到cookie
        addCookie(cart, response);
        return cart;
    }

    @Override
    public List<CartItem> delete(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cart = getCart(request);
        //id相同则删除该商品
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId().longValue() == itemId.longValue()) {
                cart.remove(i);
            }
        }

        //将购物车添加到cookie
        addCookie(cart, response);

        return cart;
    }

    /**
     * 工具方法，用于添加对象到cookie，使用base64重新编码json字符串
     * @param object
     * @param response
     */
    private void addCookie(Object object, HttpServletResponse response) {
        //使用 base64 编码
        Base64.Encoder encoder = Base64.getEncoder();
        //购物车转json
        String cartJson = JsonUtils.objectToJson(object);
        if (null != cartJson) {
            //将购物车重新添加到 Cookie 中
            Cookie cookie = new Cookie(cartCookieName, encoder.encodeToString(cartJson.getBytes()));
            //不暴露 Cookie，绑定当前 domain
            cookie.setPath("/");
            //设置 cookie 时间
            cookie.setMaxAge(60 * 60 * 24 *90);//保存90天
            response.addCookie(cookie);
        }
    }
}
