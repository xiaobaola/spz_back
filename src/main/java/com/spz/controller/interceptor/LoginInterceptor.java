package com.spz.controller.interceptor;

import com.spz.entity.manager.Manager;
import com.spz.entity.user.User;
import com.spz.service.ManagerService;
import com.spz.service.SafeService;
import com.spz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SafeService safeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 抽取成方法，用于处理不同的用户角色
        // 优化 通过路径判断来源
        log.info(request.getServletPath());
        return true;

//        return isUser(request) || isManager(request);
    }

    private boolean isManager(HttpServletRequest request) {
        // 优化：从通过username和password到token
        // 身份信息
        // 获取session
        HttpSession session = request.getSession();
        // 获取user
        Manager one = (Manager) session.getAttribute("manager");
        // 没有 返回未登录信息
        if(one == null) {
            // 抛异常 统一处理
            log.info("管理员未登录");
            return false;
        }
        // 有 去数据库中查找
        // 先找都该用户再比较
        Manager getOne = managerService.getById(one.getId());
        if(getOne == null) {
            // 抛异常 统一处理
            log.info("非法管理员");
            return false;
        }
        // 获取用户名和密码 校验
        if(!(getOne.getUsername().equals(one.getUsername())
                && getOne.getPassword().equals(one.getPassword()))) {
            // 抛异常 统一处理
            log.info("非法管理员，用户名与密码不匹配");
            return false;
        }
        return true;
    }

    private boolean isUser(HttpServletRequest request) {
        // 优化：从通过username和password到token
        // 身份信息
        // 获取session
        HttpSession session = request.getSession();
        // 获取user
        User one = (User) session.getAttribute("user");
        log.info("{}",one);
        // 没有 返回未登录信息
        if(one == null) {
            // 抛异常 统一处理
            log.info("用户未登录");
            return false;
        }
        // 有 去数据库中查找
        // 先找都该用户再比较
        User getOne = userService.getById(one.getId());
        if(getOne == null) {
            // 抛异常 统一处理
            log.info("非法用户");
            return false;
        }
        // 获取用户名和密码 校验
        if(!(getOne.getUsername().equals(one.getUsername())
                && getOne.getPassword().equals(one.getPassword()))) {
            // 抛异常 统一处理
            log.info("非法用户，用户名与密码不匹配");
            return false;
        }
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
