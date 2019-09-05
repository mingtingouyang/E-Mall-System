package org.oza.ego.sso.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.oza.ego.base.pojo.User;
import org.oza.ego.base.vo.EgoResult;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends IService<User> {

    /**
     * 检查注册参数是否重复
     * @param param 数据值
     * @param type 数据类型
     * @return 校验结果
     */
    EgoResult check(String param, Integer type);

    /**
     * 执行注册
     * @param user user对象
     * @return 操作结果
     */
    EgoResult register(User user);


    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     * @param response HttpServletResponse对象用于写入cookie
     * @return 操作结果
     */
    EgoResult login(String username, String password, HttpServletResponse response);


    /**
     * 验证登录的服务
     * @param token 存在 cookie 里的 token
     * @return 判断结果
     */
    EgoResult checkLogin(String token);

}
