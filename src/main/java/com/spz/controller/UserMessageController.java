package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.manager.Manager;
import com.spz.entity.page.PageBean;
import com.spz.entity.user.UserMessage;
import com.spz.service.UserMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        return Res.success("用户更新成功");
    }

    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              String username,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{},{}", page, pageSize, username, begin, end);
        PageBean pageBean = userMessageService.page(page, pageSize, username, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<UserMessage> getByIdNumber(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        UserMessage userMessage = userMessageService.getByIdNumber(id);
        return Res.success(userMessage);
    }

    @PostMapping
    public Res<String> insert(@RequestBody UserMessage userMessage) {
        log.info("新增: {}", userMessage);
        userMessageService.insert(userMessage);
        return Res.success("新增用户成功");
    }
}
