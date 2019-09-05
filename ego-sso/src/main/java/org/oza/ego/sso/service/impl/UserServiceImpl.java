package org.oza.ego.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.oza.ego.base.mapper.UserMapper;
import org.oza.ego.base.pojo.User;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${cookie.user}")
    private String userCookieName;

    @Value("${jedis.user.expire}")
    private Integer redisExpireTime;

    @Value("${jedis.user.key}")
    private String jedisUserKey;

    /**
     * 根据类型检查数据是否重复
     * @param param 数据值
     * @param type 数据类型
     * @return 校验结果
     */
    @Override
    public EgoResult check(String param, Integer type) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //根据类型参数执行校验
        if (null != type) {
            switch (type) {
                case 1:
                    queryWrapper.eq("username", param);
                    break;
                case 2:
                    queryWrapper.eq("phone", param);
                    break;
                case 3:
                    queryWrapper.eq("email", param);
                    break;
                default:
                    return new EgoResult(400, "待校验的数据类型有误");
            }
        } else
            return new EgoResult(400, "待校验的数据类型不能为空");

        List<User> users = userMapper.selectList(queryWrapper);

        //如果查到了，返回false
        if (null != users && users.size() > 0)
            return EgoResult.ok(false);
        //没查到则返回true
        return EgoResult.ok(true);
    }

    @Override
    public EgoResult register(User user) {
        try {
            user.setCreated(new Date());
            user.setUpdated(user.getCreated());
            userMapper.insert(user);
            return EgoResult.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            return new EgoResult(400, null);
        }

    }

    @Override
    public EgoResult login(String username, String password, HttpServletResponse response) {
        //验证用户的用户名、密码是否正确
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        List<User> users = userMapper.selectList(queryWrapper);
        //如果验证成功
        if (null != users && users.size() > 0) {
            //生成身份令牌 token
            String token = UUID.randomUUID().toString();
            //将 token 作为值，写入cookie中
            Cookie cookie = new Cookie(userCookieName, token);
            cookie.setPath("/");
            response.addCookie(cookie);

            //将用户写入到 redis 缓存中，key 为 token，值为用户信息
            User loginUser = users.get(0);
            jedisCluster.set(jedisUserKey + ":" + token, JsonUtils.objectToJson(loginUser));
            jedisCluster.expire(jedisUserKey + ":" + token, redisExpireTime);

            return EgoResult.ok(token);
        }

        //验证失败，返回错误消息
        return new EgoResult(400, "用户名或密码错误，请重新登录");
    }

    @Override
    public EgoResult checkLogin(String token) {
        //从redis中查询token
        String userJson = jedisCluster.get(jedisUserKey + ":" + token);
        //查到了数据
        if (null != userJson) {
            User user = JsonUtils.jsonToPojo(userJson, User.class);
            //每次操作后，重置超时时间
            jedisCluster.expire(jedisUserKey + ":" + token, redisExpireTime);

            return EgoResult.ok(user);
        }

        //查不到数据
        return new EgoResult(400, "用户未登录或登录失效，请重新登录");
    }
}
