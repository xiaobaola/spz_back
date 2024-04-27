package com.spz.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        log.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);
        System.out.println(methodName);

//        log.info("request:{}",request.getSession());
//        log.info("request:{}",request.getHttpServletMapping());
//        log.info("request:{}",request.getHttpServletMapping().getPattern());
//        log.info("request:{}",request.getPathInfo());
//        log.info("request:{}",request.getRequestURL());

        log.info("request:{}",request.getServletPath());

        log.info("username:{}",request.getParameter("username"));
        log.info("password:{}",request.getParameter("password"));

        log.info("userId1:{}",request.getParameter("userId1"));
        log.info("userId2:{}",request.getParameter("userId2"));
        System.out.println("拦截1");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截3");
    }
}
