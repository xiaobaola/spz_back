package com.spz.service;

import com.spz.entity.SecondHandTradeUser;

import java.util.List;

public interface SecondHandTradeUserService {
    void add(SecondHandTradeUser tradeUser);

    List<SecondHandTradeUser> getSomeByBuyerId(int buyerId);

    List<SecondHandTradeUser> getSomeBySellerId(int sellerId);

    void changeBuyerStatusByTradeId(int buyerStatus, int tradeId);

    void changeSellerStatusByTradeId(int sellerStatus, int tradeId);
}
