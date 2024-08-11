package com.spz.service.impl;

import com.spz.entity.secondhand.SecondHandTrade;
import com.spz.mapper.SecondHandTradeMapper;
import com.spz.service.SecondHandTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondHandTradeServiceImpl implements SecondHandTradeService {
    @Autowired
    private SecondHandTradeMapper secondHandTradeMapper;
    @Override
    public List<SecondHandTrade> getTradeByBuyerId(int buyerId) {
        return secondHandTradeMapper.selectByBuyerId(buyerId);
    }
}
