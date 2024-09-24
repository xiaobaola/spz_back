package com.spz.secondHand.service;

import com.spz.secondHand.entity.SecondHandTradeUser;

import java.util.List;

public interface SecondHandTradeUserService {
    void add(SecondHandTradeUser tradeUser);

    List<SecondHandTradeUser> getSomeByBuyerId(int buyerId);

    List<SecondHandTradeUser> getSomeBySellerId(int sellerId);

    void changeBuyerStatusByTradeId(int buyerStatus, int tradeId);

    void changeSellerStatusByTradeId(int sellerStatus, int tradeId);

    void changeTradeStatusByTradeId(int tradeStatus, int tradeId);
}
