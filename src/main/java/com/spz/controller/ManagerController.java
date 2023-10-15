package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.manage.Manager;
import com.spz.service.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/spz/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * @param @param manager
     * @return @return {@link Res }<{@link Manager }>
     * @author last
     * @describe 管理员登录请求
     */
    @PostMapping("/login")
    public Res<Manager> login(HttpServletRequest request, @RequestBody Manager manager) {
        log.info("login: manager{}", manager);
        Manager one = managerService.getByUN(manager);
        if(one != null) {
            //把信息写入session中
            request.getSession().setAttribute("manager", one.getId());
            return Res.success(manager);
        }
        return Res.error("用户名或密码错误");
    }

    /**
     * @param @param request
     * @return @return {@link Res }<{@link String }>
     * @author last
     * @describe 登出删除id
     */
    @PostMapping("/logout")
    public Res<String> logout(HttpServletRequest request) {
        // 清理session中保存的管理员id
        request.getSession().removeAttribute("manager");
        return Res.success("退出成功");
    }

}
