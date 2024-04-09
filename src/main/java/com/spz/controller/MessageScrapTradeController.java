package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.wrapper.Wrapper;
import com.spz.service.MessageScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Res<String> insertMessageAndScrapId(@RequestBody Wrapper wrapper) {
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
    @PutMapping("/change")
    public Res<String> updateStatusByUserId(@RequestParam Integer userId) {
        log.info("根据useId更新消息status");
        messageScrapTradeService.updateStatusByUserId(userId);
        return Res.success("更新消息状态成功");
    }
}
