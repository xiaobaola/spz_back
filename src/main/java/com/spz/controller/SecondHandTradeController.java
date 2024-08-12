package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.secondhand.SecondHandTrade;
import com.spz.entity.secondhand.SecondHandTradeDto;
import com.spz.entity.user.User;
import com.spz.entity.wrapper.SecondHandWrapper;
import com.spz.service.SecondHandTradeService;
import jakarta.servlet.http.HttpServletRequest;
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
        log.info("购买二手物品，创建订单，参数{}",secondHandWrapper);
        SecondHandTrade trade = new SecondHandTrade();
        trade.setApproach(secondHandWrapper.getApproach());
        trade.setTradeTime(secondHandWrapper.getTradeTime());
        trade.setPlace(secondHandWrapper.getPlace());
        int userId = secondHandWrapper.getUserId();
        // 安全
        userId = User.getUserIdBySession(userId, request);
        int itemId = secondHandWrapper.getItemId();
        // 流程比较负责 涉及到订单的uuid创建， item的status，订单的status
        secondHandTradeService.createByBuyerIdAndItemIdAndTrade(userId,itemId,trade);
        return Res.success("购买成功");
    }

    @GetMapping("/list/buyer")
    public Res<List<SecondHandTradeDto>> listBuyer(@RequestParam int userId,HttpServletRequest request) {
        userId = User.getUserIdBySession(userId,request);
        log.info("买家查询买入订单信息，参数buyerId:{}",userId);
        return Res.success(secondHandTradeService.getTradeDtoListByBuyerId(userId));
    }
    @GetMapping("/list/seller")
    public Res<List<SecondHandTradeDto>> listSeller(@RequestParam int userId,HttpServletRequest request) {
        userId = User.getUserIdBySession(userId,request);
        log.info("买家查询买入订单信息，参数buyerId:{}",userId);
        return Res.success(secondHandTradeService.getTradeDtoListBySellerId(userId));
    }
}
