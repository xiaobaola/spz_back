package com.spz.service;

import com.spz.entity.SecondHandTrade;
import com.spz.entity.dto.SecondHandTradeDto;

import java.util.List;

public interface SecondHandTradeService {
    List<SecondHandTrade> getTradeByBuyerId(int buyerId);

    void addByBuyerIdAndItemIdAndTrade(int buyerId, int itemId, SecondHandTrade trade);

    List<SecondHandTradeDto> getTradeDtoListByBuyerId(int buyerId);

    List<SecondHandTradeDto> getTradeDtoListBySellerId(int sellerId);

    void changeBuyerTradeBuyerStatusByTradeId(int tradeId);

    void changeSellerTradeSellerStatusByTradeId(int tradeId);
}
