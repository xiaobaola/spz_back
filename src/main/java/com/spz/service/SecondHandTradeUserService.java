package com.spz.service;

import com.spz.entity.secondhand.SecondHandTradeUser;

import java.util.List;

public interface SecondHandTradeUserService {
    void insertOne(SecondHandTradeUser tradeUser);

    List<SecondHandTradeUser> getSomeByBuyerId(int buyerId);

    List<SecondHandTradeUser> getSomeBySellerId(int sellerId);

    void changeBuyerStatusByTradeId(int buyerStatus, int tradeId);

    void changeSellerStatusByTradeId(int sellerStatus, int tradeId);
}
