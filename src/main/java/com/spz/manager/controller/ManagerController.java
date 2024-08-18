package com.spz.manager.controller;

import com.spz.public_resource.common.Res;
import com.spz.manager.entity.Manager;
import com.spz.public_resource.entity.page.PageBean;
import com.spz.manager.service.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/spz/manager")
public class ManagerController {


    private ManagerService managerService;

    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    /**
     * Param @param manager
     * Return @return {@link Res }<{@link Manager }>
     * Author last
     * Describe 管理员登录请求
     */
    @PostMapping("/login")
    public Res<Manager> login(HttpServletRequest request, @RequestBody Manager manager) {
        log.info("login: manager{}", manager);
        Manager one = managerService.getByUN(manager);
        if(one != null) {
            //把信息写入session中
            request.getSession().setAttribute("manager", one);
            return Res.success(one);
        }
        return Res.error("用户名或密码错误");
    }

    /**
     * Param @param request
     * Return @return {@link Res }<{@link String }>
     * Author last
     * Describe 登出删除id
     */
    @PostMapping("/logout")
    public Res<String> logout(HttpServletRequest request) {
        // 清理session中保存的管理员id
        request.getSession().removeAttribute("manager");
        return Res.success("退出成功");
    }

    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              String name,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{}", page, pageSize, name, begin, end);
        PageBean pageBean = managerService.page(page, pageSize, name, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<Manager> getById(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        Manager manager = managerService.getById(id);
        return Res.success(manager);
    }

    @PutMapping
    public Res<String> updateById(@RequestBody Manager manager,HttpServletRequest request) {
        log.info("更新: {}", manager);
        int managerId = manager.getId();
        managerId = Manager.getManagerIdByRequest(managerId,request);
        manager.setId(managerId);
        managerService.updateById(manager);
        return Res.success("修改开发人员信息成功");
    }

    @PostMapping
    public Res<String> insert(@RequestBody Manager manager) {
        log.info("新增: {}", manager);
        managerService.insert(manager);
        return Res.success("新增开发人员成功");
    }
}
