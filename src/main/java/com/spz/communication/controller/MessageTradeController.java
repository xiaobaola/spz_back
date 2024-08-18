package com.spz.communication.controller;


import com.spz.public_resource.common.Res;
import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.message.dto.MessageTradeDto;
import com.spz.personal.entity.User;
import com.spz.communication.service.MessageTradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spz/messageTrade")
@Slf4j
public class MessageTradeController {

    private MessageTradeService messageTradeService;
    @Autowired
    public void setMessageTradeService(MessageTradeService messageTradeService) {
        this.messageTradeService = messageTradeService;
    }

    @PostMapping
    public Res<String> createMessageTrade(@RequestBody MessageTrade messageTrade){
        log.info("新增: {}", messageTrade);
        messageTradeService.add(messageTrade);
        return Res.success("新增信息成功");
    }

    @GetMapping("/list")
    public Res<List<MessageTrade>> selectMessageTradeAll(){
        log.info("get 信息列表");
        return Res.success(messageTradeService.getList());
    }

    @GetMapping("/{id}")
    public Res<MessageTrade> selectMessageTradeById(@PathVariable Integer id){
        log.info("get 信息列表 id:{}",id);
        return Res.success(messageTradeService.getById(id));
    }

    @GetMapping()
    public Res<List<MessageTradeDto>> getAllByMessageTradeId(@RequestParam Integer userId, HttpServletRequest request){
        log.info("get 信息列表 userId:{}",userId);
        // 20240809安全优化userId
        userId = User.getUserIdBySession(userId,request);
        return Res.success(messageTradeService.getMessageTradeDtosByUserId(userId));
    }

}
