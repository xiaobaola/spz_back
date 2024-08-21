package com.spz.personal.controller;

import com.spz.common.Res;
import com.spz.entity.page.PageBean;
import com.spz.personal.entity.User;
import com.spz.personal.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/spz/user")
@RestController
@Slf4j
public class UserController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Res<User> getById(User user) {
        log.info("get：用户id{}", user.getId());
        user = userService.getById(user.getId());
        return Res.success(user);
    }
    @PostMapping("/login")
    //HttpServletRequest request,
    public Res<User> login(@RequestBody User userMessage, HttpServletRequest request) {
        // 可以考虑使用md5加密
        String username = userMessage.getUsername();
        String password = userMessage.getPassword();
        log.info("login请求 username:{}, password{}", username, password);
        User user = userService.getByUsernameAndPassword(username, password);
        if(user != null) {
            log.info("user = {}", user);
            // session
            HttpSession session = request.getSession();
            // 向session设置值
            session.setAttribute("user",user);
            // token
            return Res.success(user);
        }
        return Res.error("用户名或密码错误");
    }

    @PostMapping("/logout")
    //HttpServletRequest request,
    public Res<String> logout(HttpServletRequest request) {
        // 清理session中保存的管理员id
        request.getSession().removeAttribute("user");
        return Res.success("退出成功");
    }


    /**
     * Author last
     * Param @param page 页
     * Describe 为后台管理员提供用户信息的分页查询
     * @param pageSize 页面大小
     * @param username 用户名
     * @param begin    开始
     * @param end      结束
     *                 Return @return {@link Res }<{@link PageBean }>
     *                 Describe 页
     */
    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              String username,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{}", page, pageSize, username, begin, end);
        PageBean pageBean = userService.page(page, pageSize, username, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<User> getByIdNumber(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        User user = userService.getById(id);
        return Res.success(user);
    }

    @PostMapping
    public Res<String> insert(@RequestBody User user) {
        log.info("新增: {}", user);
        userService.insert(user);
        return Res.success("新增用户成功");
    }

    @PutMapping
    public Res<String> updateById(@RequestBody User user, HttpServletRequest request) {
        // 20240809安全优化 session->userId
        int userId = user.getId();
        userId = User.getUserIdBySession(userId,request);
        user.setId(userId);
        log.info("userMessage{}", user);
        userService.changeById(user);
        return Res.success("用户更新成功");
    }

    @PostMapping("/login/wx")
    //HttpServletRequest request,
    public Res<User> wxLogin(@RequestBody String code, HttpServletRequest request) {
        log.info("wxLogin请求 code:{}", code);
        // 可以考虑使用md5加密
        String username = "评委·";
        String password = "123456";
        log.info("login请求 username:{}, password{}", username, password);
        User user = userService.getByUsernameAndPassword(username, password);
        if(user != null) {
            log.info("user = {}", user);
            // session
            HttpSession session = request.getSession();
            // 向session设置值
            session.setAttribute("user",user);
            // token
            return Res.success(user);
        }
        return Res.error("用户名或密码错误");
    }
}
