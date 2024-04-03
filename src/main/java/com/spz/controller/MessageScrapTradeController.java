package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.service.MessageScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spz/messageScrapTrade")
public class MessageScrapTradeController {
    @Autowired
    private MessageScrapTradeService messageScrapTradeService;

    @GetMapping("/message/{id}")
    public Res<MessageScrapTrade> getMessageTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getMessageTradeIdById(id));
    }

    @GetMapping("/scrap/{id}")
    public Res<MessageScrapTrade> getScrapTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getScrapTradeIdById(id));
    }

}
