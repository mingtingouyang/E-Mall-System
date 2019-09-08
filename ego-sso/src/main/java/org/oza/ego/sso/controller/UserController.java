package org.oza.ego.sso.controller;

import org.oza.ego.base.pojo.User;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 检查用户名及手机号是否有重复
     * @param param 数据值
     * @param type 数据类型
     * @param callback 回调函数名
     * @return 检查结果
     */
    @RequestMapping("/check/{param}/{type}")
    public Object check(@PathVariable String param, @PathVariable Integer type, String callback) {
        EgoResult result = userService.check(param, type);

        //如果没有指定回调函数，则直接返回结果对象
        if (null == callback || "".equals(callback.trim()))
            return result;
        //如果指定了回调函数，则封装 jsonp
        String resultJson = JsonUtils.objectToJson(result);
        resultJson = callback + "(" + resultJson + ")";
        return resultJson;
    }

    /**
     * 注册新用户
     * @param user User 对象
     * @return 操作结果
     */
    @RequestMapping("/register")
    public EgoResult register(User user) {
        return userService.register(user);
    }

    /**
     * 登录用户：登录成功则替用户生成token存放于cookie中，同时将用户信息存于redis缓存中，缓存的key就是token
     * @param username 用户名
     * @param password 密码
     * @param response 用于添加 cookie
     * @return 操作结果
     */
    @PostMapping("/login")
    public EgoResult login(String username, String password, HttpServletResponse response) {
        return userService.login(username, password, response);
    }

    /**
     * 单点登录系统中用于给其他服务判断是否登录的接口
     * @param token 身份令牌
     * @param callback 回调函数名
     * @return 操作结果
     */
    @RequestMapping("/token/{token}")
    public Object checkLogin(@PathVariable String token, String callback) {
        EgoResult result = userService.checkLogin(token);

        //如果没有回调函数，直接返回结果对象
        if (null == callback) {
            return result;
        }

        //否则封装为jsonp
        String resultJson = callback + "(" + JsonUtils.objectToJson(result) + ")";
        return resultJson;
    }

}
