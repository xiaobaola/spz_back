package com.spz.secondHand.service;

import com.spz.secondHand.entity.SecondHandTrade;
import com.spz.secondHand.entity.dto.SecondHandTradeDto;

import java.util.List;

public interface SecondHandTradeService {
    List<SecondHandTrade> getTradeByBuyerId(int buyerId);

    void createByBuyerIdAndItemIdAndTrade(int buyerId, int itemId, SecondHandTrade trade);

    SecondHandTrade getOneById(int secondHandTradeId);

    List<SecondHandTradeDto> getTradeDtoListByBuyerId(int buyerId);

    List<SecondHandTradeDto> getTradeDtoListBySellerId(int sellerId);

    void buyerChangeTradeBuyerStatusByTradeId(int tradeId);

    void sellerChangeTradeSellerStatusByTradeId(int tradeId);
}
