package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.service.MessageScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spz/messageScrapTrade")
public class MessageScrapTradeController {
    @Autowired
    private MessageScrapTradeService messageScrapTradeService;

    @GetMapping("/message/{id}")
    public Res<List<Integer>> getMessageTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getMessageTradeIdById(id));
    }

    @GetMapping("/scrap/{id}")
    public Res<List<Integer>> getScrapTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getScrapTradeIdById(id));
    }

    @PostMapping
    public Res<String> insertMessageAndScrapId(@RequestBody List<Integer> scrapTradeIds, @RequestParam Integer messageTradeId){
        messageScrapTradeService.insertByid(scrapTradeIds,messageTradeId);
        return Res.success("添加成功");
    }

}
