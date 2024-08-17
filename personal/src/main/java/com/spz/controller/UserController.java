package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.User;
import com.spz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/spz/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Res<User> getById(User user) {
        log.info("get：用户id{}", user.getId());
        user = userService.getById(user.getId());
        return Res.success(user);
    }
    @PostMapping("/login")
    //HttpServletRequest request,
    public Res<User> login(@RequestBody User userMessage, HttpServletRequest request) {
        String username = userMessage.getUsername();
        String password = userMessage.getPassword();
        log.info("login请求 username:{}, password{}", username, password);
        User user = userService.getByUsernameAndPassword(username,password);
        if(user != null) {
            log.info("user = {}", user);
            // session
            HttpSession session = request.getSession();
            // 向session设置值
            session.setAttribute("user",user);
            // token 优化 当采用nginx 和 微服务架构时需要使用token
            return Res.success(user);
        }
        return Res.error("用户名或密码错误");
    }

    @PostMapping("/logout")
    //HttpServletRequest request,
    public Res<String> logout(@RequestBody User user) {
        // 清理session中保存的管理员id
//        request.getSession().removeAttribute("user");
        return Res.success("退出成功");
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
        userService.updateById(user);
        return Res.success("用户更新成功");
    }

}
