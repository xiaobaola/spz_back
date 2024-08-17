package com.spz.communication.controller;

import com.spz.public_resource.common.Res;
import com.spz.communication.entity.message.MessageUser;
import com.spz.personal.entity.User;
import com.spz.communication.service.MessageUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/messageUser")
@Slf4j
public class MessageUserController {
    @Autowired
    MessageUserService messageUserService;

    @GetMapping("/list")
    Res<List<MessageUser>> list(@RequestParam Integer userId1, @RequestParam Integer userId2, HttpServletRequest request) {
        // 20240808安全优化 session->userid
        userId1 = User.getUserIdBySession(userId1,request);
        log.info("list好友信息获取请求，参数userId1:{},userId2:{}",userId1,userId2);
        return Res.success(messageUserService.list(userId1, userId2));
    }
    @PostMapping
    Res<String> insertMessageUser(@RequestBody MessageUser messageUser) {
        log.info("参数信息{}", messageUser);
        messageUserService.insert(messageUser);
        return Res.success("信息发送成功");
    }
}
