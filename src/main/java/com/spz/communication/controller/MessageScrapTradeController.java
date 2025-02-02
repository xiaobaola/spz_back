package com.spz.communication.controller;

import com.spz.public_resource.common.Res;
import com.spz.personal.entity.User;
import com.spz.communication.entity.message.wrapper.MessageWrapper;
import com.spz.communication.service.MessageScrapTradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("spz/messageScrapTrade")
public class MessageScrapTradeController {

    private MessageScrapTradeService messageScrapTradeService;

    @Autowired
    public void setMessageScrapTradeService(MessageScrapTradeService messageScrapTradeService) {
        this.messageScrapTradeService = messageScrapTradeService;
    }

    @GetMapping("/message/{id}")
    public Res<List<Integer>> getMessageTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getMessageTradeIdById(id));
    }

    @GetMapping("/scrap/{id}")
    public Res<List<Integer>> getScrapTradeId(@PathVariable Integer id){
        return Res.success(messageScrapTradeService.getScrapTradeIdById(id));
    }

    @PostMapping()
    public Res<String> insertMessageAndScrapId(@RequestBody MessageWrapper messageWrapper) {
        List<Integer> scrapTradeIds = messageWrapper.getScrapTradeIds();
        Integer messageTradeId = messageWrapper.getMessageTradeId();
        messageScrapTradeService.addById(scrapTradeIds,messageTradeId);
        return Res.success("添加成功");
    }

    @GetMapping("/total")
    public Res<Integer> getTotalByUserId(@RequestParam Integer userId, HttpServletRequest request){
        // 20240809安全优化userId
        userId = User.getUserIdBySession(userId,request);
        log.info("get2 总数");
        return Res.success(messageScrapTradeService.getTotalByMessageScrapTrade(userId));
    }
    @PutMapping("/change")
    public Res<String> updateStatusByUserId(@RequestBody MessageWrapper messageWrapper, HttpServletRequest request) {
        log.info("根据useId更新消息status");
        log.info("wrapper: {}", messageWrapper);
        int userId = messageWrapper.getUserId();
        // 20240809安全优化userId
        userId = User.getUserIdBySession(userId,request);
//        log.info("userId: {}", userId);
        messageScrapTradeService.changeStatusByUserId(userId);
        return Res.success("更新消息状态成功");
    }

}
