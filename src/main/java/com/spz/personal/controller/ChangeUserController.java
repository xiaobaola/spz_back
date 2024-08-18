package com.spz.personal.controller;

import com.spz.personal.service.UserService;
import com.spz.public_resource.common.Res;
import com.spz.personal.entity.User;
import com.spz.personal.entity.UserChangeWrapper;
import com.spz.personal.service.ChangeUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/spz/user")
@Slf4j
// 20240715改变用户信息的接口为什么不放在用户信息的接口中
// 为了方便维护？
public class ChangeUserController {


    private ChangeUserService changeUserService;


    private UserService userService;

    @Autowired
    public void setChangeUserService(ChangeUserService changeUserService) {
        this.changeUserService = changeUserService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    ArrayList<User> users;

    @PutMapping("/changeUserName")
    public Res<String> ChangeUserName(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
        // 20240715根据数据库用户信息判断后执行修改
        users = userService.getByAll();
        log.info("请求 user:{}", userChangeWrapper);
        Integer userId = userChangeWrapper.getUserId();
        // 20240808安全优化 session->userid 抽取成user的一个方法，因为会经常用到
        userId = User.getUserIdBySession(userId,request);
        String userName = userChangeWrapper.getUserName();
        for (User user : users) {
            if (userName.equals(user.getUsername())) {
                return Res.error("修改失败，用户名已存在");
            }
        }
        changeUserService.changeUserName(userId, userName);
        return Res.success("修改成功");
    }

    @PutMapping("/changePhone")
    public Res<String> ChangePhone(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
        users = userService.getByAll();
        log.info("请求 user:{}", userChangeWrapper);
        Integer userId = userChangeWrapper.getUserId();
        // 20240808安全优化 session->userid 抽取成user的一个方法，因为会经常用到
        userId = User.getUserIdBySession(userId,request);
        String phone = userChangeWrapper.getPhone();
        int count = 0;
        for (User user1 : users) {
            if (phone.equals(user1.getPhone())) {
                count++;
                if (count == 3) {
                    return Res.error("修改失败，电话号码已存在三次");
                }
            }
        }
        changeUserService.changePhone(userId, phone);
        return Res.success("修改成功");
    }

    @PutMapping("/changePassword")
    public Res<String> ChangePassword(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
        log.info("请求 user:{}", userChangeWrapper);
        Integer userId = userChangeWrapper.getUserId();
        // 20240808安全优化 session->userid
        userId = User.getUserIdBySession(userId,request);
        log.info("changePassword请求 userId:{}", userId);

        String password = userChangeWrapper.getPassword();
        changeUserService.changePassword(userId, password);
        return Res.success("修改成功");
    }

}
