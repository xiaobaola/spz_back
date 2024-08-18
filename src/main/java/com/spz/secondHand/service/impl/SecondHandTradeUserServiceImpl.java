package com.spz.secondHand.service.impl;

import com.spz.secondHand.entity.SecondHandTradeUser;
import com.spz.secondHand.mapper.SecondHandTradeUserMapper;
import com.spz.secondHand.service.SecondHandTradeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondHandTradeUserServiceImpl implements SecondHandTradeUserService {

    private SecondHandTradeUserMapper tradeUserMapper;

    @Autowired
    public void setTradeUserMapper(SecondHandTradeUserMapper tradeUserMapper) {
        this.tradeUserMapper = tradeUserMapper;
    }

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
