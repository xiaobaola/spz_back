package com.spz.personal_extend.controller;

import com.spz.personal_extend.entity.User;
import com.spz.personal_extend.entity.wrapper.UserChangeWrapper;
import com.spz.personal_extend.service.UserChangeUserService;
import com.spz.personal_extend.service.UserService;
import com.spz.common.Res;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/spz/user")
@Slf4j
@RequiredArgsConstructor
// 20240715改变用户信息的接口为什么不放在用户信息的接口中
// 为了方便维护？
public class ChangeUserController {

    private final UserChangeUserService changeUserService;

    private final UserService userService;


    ArrayList<User> users;

    @PutMapping("/changeUserName")
    public Res<String> changeUserName(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
        // 20240715根据数据库用户信息判断后执行修改
        users = userService.getList();
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
    public Res<String> changePhone(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
        users = userService.getList();
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
    public Res<String> changePassword(@RequestBody UserChangeWrapper userChangeWrapper, HttpServletRequest request) {
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
