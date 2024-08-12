package com.spz.service.impl;

import com.spz.entity.secondhand.SecondHandTrade;
import com.spz.mapper.SecondHandTradeMapper;
import com.spz.service.SecondHandItemService;
import com.spz.service.SecondHandTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondHandTradeServiceImpl implements SecondHandTradeService {
    @Autowired
    private SecondHandTradeMapper secondHandTradeMapper;
    @Autowired
    private SecondHandItemService secondHandItemService;
    @Override
    public List<SecondHandTrade> getTradeByBuyerId(int buyerId) {
        return secondHandTradeMapper.selectByBuyerId(buyerId);
    }

    @Override
    public void createSecondHandTradeByBuyerIdAndItemId(int buyerId, int itemId) {
        // 1物品状态变为下架状态 2->3
        // 2生成订单的uuid
        // 3订单的状态设置成1
    }
}
