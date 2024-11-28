package com.spz.secondHand.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.secondHand.entity.SecondHandTrade;
import com.spz.secondHand.entity.dto.SecondHandTradeDto;

import java.util.List;

public interface SecondHandTradeService extends IService<SecondHandTrade> {
    List<SecondHandTrade> getTradeByBuyerId(int buyerId);

    void addByBuyerIdAndItemIdAndTrade(int buyerId, int itemId, SecondHandTrade trade);

    List<SecondHandTradeDto> getTradeDtoListByBuyerId(int buyerId);

    List<SecondHandTradeDto> getTradeDtoListBySellerId(int sellerId);

    void changeBuyerTradeBuyerStatusByTradeId(int status, int tradeId);

    void changeSellerTradeSellerStatusByTradeId(int status, int tradeId);

}
