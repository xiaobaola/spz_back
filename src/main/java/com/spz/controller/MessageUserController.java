package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.message.MessageUser;
import com.spz.entity.user.User;
import com.spz.service.MessageUserService;
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
    Res<List<MessageUser>> list(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        log.info("list好友信息获取请求，参数userId1:{},userId2:{}",userId1,userId2);
        return Res.success(messageUserService.list(userId1, userId2));
    }
}
