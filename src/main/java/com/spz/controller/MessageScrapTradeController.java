package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.communicate.ScrapIdsWrapper;
import com.spz.service.MessageScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
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

    @PostMapping()
    public Res<String> insertMessageAndScrapId(@RequestBody ScrapIdsWrapper wrapper) {
        List<Integer> scrapTradeIds = wrapper.getScrapTradeIds();
        Integer messageTradeId = wrapper.getMessageTradeId();
        messageScrapTradeService.insertByid(scrapTradeIds,messageTradeId);
        return Res.success("添加成功");
    }

    @GetMapping("/total")
    public Res<Integer> getTotalByUserId(@RequestParam Integer userId){
        log.info("get2 总数");
        return Res.success(messageScrapTradeService.getTotalByMessageScrapTrade(userId));
    }
}
