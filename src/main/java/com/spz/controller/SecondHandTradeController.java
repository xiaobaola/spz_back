package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.secondhand.SecondHandTrade;
import com.spz.entity.user.User;
import com.spz.entity.wrapper.SecondHandWrapper;
import com.spz.service.SecondHandTradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/trade")
@Slf4j
public class SecondHandTradeController {
    @Autowired
    private SecondHandTradeService secondHandTradeService;

    @GetMapping("/buyer")
    public Res<List<SecondHandTrade>> buyerList(@RequestParam int userId, HttpServletRequest request){
        // 通过buyerId得到二手交易订单
        userId = User.getUserIdBySession(userId,request);
        log.info("获取买家二手交易记录，参数userId:{}",userId);
        return Res.success(secondHandTradeService.getTradeByBuyerId(userId));
    }

    @PostMapping
    public Res<String> buySecondHandItem(@RequestBody SecondHandWrapper secondHandWrapper, HttpServletRequest request) {
        int userId = secondHandWrapper.getUserId();
        int itemId = secondHandWrapper.getItemId();
        userId = User.getUserIdBySession(userId, request);
        // 流程比较负责 涉及到订单的uuid创建， item的status，订单的status
        return Res.success("购买成功");
    }
}
