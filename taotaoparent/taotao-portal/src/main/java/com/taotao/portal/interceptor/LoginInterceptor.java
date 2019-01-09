package com.taotao.portal.interceptor;

import com.taotao.pojo.TbUser;
import com.taotao.portal.Common;
import com.taotao.portal.IKey;
import com.taotao.portal.service.UserService;
import com.taotao.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor,Common,IKey {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在Handler执行之前处理
        //1.判断是否登录
        //2.取token
        String token = CookieUtils.getCookieValue(request,"TT_TOKEN");
        //3.通过token取用户信息，并判断用户信息是否可用，如果不可用则需要登录逻辑，跳转到登录页面，并把用户请求的url作为参数传递给登录页面
        TbUser tbUser = userService.getUserByToken(token);
        if (null == tbUser){
            response.sendRedirect(M_PORTAL_PAGE_LOGIN+"?redirect="+request.getRequestURL() );
            return false;
        }

        request.setAttribute(KEY_USER,tbUser);

        //返回Handler是否执行，返回true=执行，false=不执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在Handler执行之后处理，返回ModelAndView之前

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在Handler执行之后处理，返回ModelAndView之后


    }
}
