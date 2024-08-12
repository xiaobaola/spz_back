package com.spz.service;

import com.spz.entity.secondhand.SecondHandTrade;

import java.util.List;

public interface SecondHandTradeService {
    List<SecondHandTrade> getTradeByBuyerId(int buyerId);

    void createSecondHandTradeByBuyerIdAndItemId(int buyerId, int itemId);
}
