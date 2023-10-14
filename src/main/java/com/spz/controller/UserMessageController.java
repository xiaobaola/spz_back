package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.UserMessage;
import com.spz.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/spz/user")
@RestController
@Slf4j
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;

    @GetMapping
    public Res<UserMessage> getById(UserMessage userMessage) {
        log.info("get：用户id{}",userMessage.getId());
        userMessage = userMessageService.getById(userMessage);
        return Res.success(userMessage);
    }
    @GetMapping("/login")
    public Res<UserMessage> login(UserMessage userMessage) {
        String username = userMessage.getUsername();
        String password = userMessage.getPassword();
        log.info("login请求 username:{}, password{}", username, password);
        UserMessage user = userMessageService.getByInfo(userMessage);
        if(user != null) {
            log.info("user = {}", user);
            return Res.success(user);
        }
        return Res.error("用户名或密码错误");
    }
    @PutMapping
    public Res<String> updateById(@RequestBody UserMessage userMessage) {
        log.info("userMessage{}", userMessage);
        userMessageService.updeteById(userMessage);
        return Res.success("更新成功");
    }
}
