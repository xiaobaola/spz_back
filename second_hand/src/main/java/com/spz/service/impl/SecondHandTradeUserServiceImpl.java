package com.spz.service.impl;

import com.spz.entity.secondhand.SecondHandTradeUser;
import com.spz.mapper.SecondHandTradeUserMapper;
import com.spz.service.SecondHandTradeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondHandTradeUserServiceImpl implements SecondHandTradeUserService {
    @Autowired
    private SecondHandTradeUserMapper tradeUserMapper;
    @Override
    public void insertOne(SecondHandTradeUser tradeUser) {
        tradeUserMapper.insertOne(tradeUser);
    }

    @Override
    public List<SecondHandTradeUser> getSomeByBuyerId(int buyerId) {
        return tradeUserMapper.selectSomeByBuyerId(buyerId);
    }

    @Override
    public List<SecondHandTradeUser> getSomeBySellerId(int sellerId) {
        return tradeUserMapper.selectSomeBySellerId(sellerId);
    }

    @Override
    public void changeBuyerStatusByTradeId(int buyerStatus, int tradeId) {
        tradeUserMapper.updateBuyerStatusByTradeId(buyerStatus, tradeId);
    }

    @Override
    public void changeSellerStatusByTradeId(int sellerStatus, int tradeId) {
        tradeUserMapper.updateSellerStatusByTradeId(sellerStatus, tradeId);
    }
}
