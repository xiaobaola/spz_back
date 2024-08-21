package com.spz.secondHand.controller;

import com.spz.common.Res;
import com.spz.secondHand.entity.SecondHandTrade;
import com.spz.secondHand.entity.dto.SecondHandTradeDto;
import com.spz.personal.entity.User;
import com.spz.secondHand.entity.wrapper.SecondHandWrapper;
import com.spz.secondHand.service.SecondHandTradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/trade")
@Slf4j
public class SecondHandTradeController {

    private SecondHandTradeService tradeService;

    @Autowired
    public void setTradeService(SecondHandTradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping("/buyer")
    public Res<List<SecondHandTrade>> buyerList(@RequestParam int userId, HttpServletRequest request){
        // 通过buyerId得到二手交易订单
        userId = User.getUserIdBySession(userId,request);
        log.info("获取买家二手交易记录，参数userId:{}",userId);
        return Res.success(tradeService.getTradeByBuyerId(userId));
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
        tradeService.addByBuyerIdAndItemIdAndTrade(userId,itemId,trade);
        return Res.success("购买成功");
    }

    @GetMapping("/list/buyer")
    public Res<List<SecondHandTradeDto>> listBuyer(@RequestParam int userId, HttpServletRequest request) {
        userId = User.getUserIdBySession(userId,request);
        log.info("买家查询买入订单信息，参数buyerId:{}",userId);
        return Res.success(tradeService.getTradeDtoListByBuyerId(userId));
    }
    @GetMapping("/list/seller")
    public Res<List<SecondHandTradeDto>> listSeller(@RequestParam int userId,HttpServletRequest request) {
        userId = User.getUserIdBySession(userId,request);
        log.info("买家查询买入订单信息，参数buyerId:{}",userId);
        return Res.success(tradeService.getTradeDtoListBySellerId(userId));
    }
    @PutMapping("/buyer")
    public Res<String> buyerCancelTradeByBuyerIdAndTradeId(@RequestBody SecondHandWrapper wrapper){
//        int userId = wrapper.getUserId();
//        userId = User.getUserIdBySession(userId,request);
        // 实际上只需要一个tradeId，userId是用作安全校验，校验订单是否来源与买家
        int tradeId = wrapper.getTradeId();
//        log.info("买家取消订单，参数userId:{},tradeId:{}",userId,tradeId);
        log.info("买家取消订单，参数tradeId:{}",tradeId);
        tradeService.changeBuyerTradeBuyerStatusByTradeId(tradeId);
        return Res.success("已经取消买进");
    }

    @PutMapping("/seller")
    public Res<String> sellerCancelTradeBySTradeId(@RequestBody SecondHandWrapper wrapper) {
        // 与buyerCancel同理，可以进行优化
        int tradeId = wrapper.getTradeId();
        log.info("卖家取消订单，参数tradeId:{}",tradeId);
        tradeService.changeSellerTradeSellerStatusByTradeId(tradeId);
        return Res.success("已经取消卖出");
    }
}
