package com.spz.controller;


import com.spz.common.Res;
import com.spz.entity.communicate.MessageTrade;
import com.spz.service.MessageTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("spz/messageTrade")
@Slf4j
public class MessageTradeController {
    @Autowired
    private MessageTradeService messageTradeService;

    @PostMapping
    public Res<String> createMessageTrade(@RequestBody MessageTrade messageTrade){
        log.info("新增: {}", messageTrade);
        messageTradeService.insert3(messageTrade);
        return Res.success("新增信息成功");
    }

    @GetMapping("/list")
    public Res<List<MessageTrade>> selectMessageTradeAll(){
        log.info("get 信息列表");
        return Res.success(messageTradeService.list3());
    }

    @GetMapping("/{id}")
    public Res<MessageTrade> selectMessageTradeById(@PathVariable Integer id){
        log.info("get 信息列表");
        return Res.success(messageTradeService.getById(id));
    }

}
