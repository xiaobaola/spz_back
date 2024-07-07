package com.spz.controller.interceptor;

import com.spz.common.Res;
import com.spz.entity.safe.Token;
import com.spz.entity.user.User;
import com.spz.service.SafeService;
import com.spz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SafeService safeService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 抽取成方法，用于处理不同的用户角色
        // 身份信息
        // 获取session
        HttpSession session = request.getSession();
        // 获取user
        User one = (User) session.getAttribute("user");
        // 没有 返回未登录信息
        if(one == null) {
            // 抛异常 统一处理
            log.info("未登录");
            return false;
        }
        // 有 去数据库中查找

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("拦截2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       // 请求完成返回后的操作
    }
}
