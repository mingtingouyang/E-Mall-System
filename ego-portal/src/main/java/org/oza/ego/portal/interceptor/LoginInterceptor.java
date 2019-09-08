package org.oza.ego.portal.interceptor;

import org.oza.ego.base.pojo.User;
import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.vo.EgoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登录校验拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${cookie.user}")
    private String userCookieName;

    @Value("${sso.baseUrl}")
    private String ssoBaseUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User loginUser = null;
        //获得cookie
        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            //取得token并远程校验
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(userCookieName)) {
                    String token = cookie.getValue();
                    //调用sso的接口校验
                    String resultJson = HttpClientUtils.doGet(ssoBaseUrl + "/token/" + token, null);
                    if (null != resultJson) {
                        EgoResult egoResult = EgoResult.formatToEgoResult(resultJson, User.class);
                        //校验成功则将用户信息获取
                        if(egoResult.getStatus() == 200) {
                            loginUser = (User)egoResult.getData();
                        }
                    }
                    break;
                }
            }
        }
        //如果校验失败，登录用户为空，重定向到登录页面
        if (loginUser == null) {
            response.sendRedirect(ssoBaseUrl + "/showLogin");
            return false;
        }

        //校验成功
        request.getSession().setAttribute("loginUser", loginUser);
//        request.setAttribute("loginUser", loginUser);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
