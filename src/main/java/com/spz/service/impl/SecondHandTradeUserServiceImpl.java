package com.spz.service.impl;

import com.spz.entity.secondhand.SecondHandTradeUser;
import com.spz.mapper.SecondHandTradeUserMapper;
import com.spz.service.SecondHandTradeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondHandTradeUserServiceImpl implements SecondHandTradeUserService {
    @Autowired
    private SecondHandTradeUserMapper secondHandTradeUserMapper;
    @Override
    public void insertOne(SecondHandTradeUser tradeUser) {
        secondHandTradeUserMapper.insertOne(tradeUser);
    }
}
