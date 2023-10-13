package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.UserMessage;
import com.spz.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Res<Integer> login(UserMessage userMessage) {
        String username = userMessage.getUsername();
        String password = userMessage.getPassword();
        log.info("login请求 username:{}, password{}", username, password);
        UserMessage one = userMessageService.getByInfo(userMessage);
        if(one != null) {
            log.info("userId = {}", one.getId());
            return Res.success(one.getId());
        }
        return Res.error("用户名或密码错误");
    }
}
