package com.spz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.spz.entity.secondhand.SecondHandItem;
import com.spz.entity.secondhand.SecondHandTrade;
import com.spz.entity.secondhand.SecondHandTradeUser;
import com.spz.mapper.SecondHandTradeMapper;
import com.spz.service.SecondHandItemService;
import com.spz.service.SecondHandTradeService;
import com.spz.service.SecondHandTradeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SecondHandTradeServiceImpl implements SecondHandTradeService {
    @Autowired
    private SecondHandTradeMapper secondHandTradeMapper;
    @Autowired
    private SecondHandItemService secondHandItemService;
    @Autowired
    private SecondHandTradeUserService secondHandTradeUserService;
    @Override
    public List<SecondHandTrade> getTradeByBuyerId(int buyerId) {
        return secondHandTradeMapper.selectByBuyerId(buyerId);
    }

    @Override
    public void createByBuyerIdAndItemIdAndTrade(int buyerId, int itemId, SecondHandTrade trade) {
        // 1物品状态变为下架状态 2->3
        // 可以优化 防止多次购物，加锁，加条件判断
        secondHandItemService.changeStatusById(3,itemId);
        // 2 创建二手交易订单
        // 2.1生成订单的编号
        long orderId = IdWorker.getId();
        String number = String.valueOf(orderId);
        // 补全
        trade.setNumber(number);
        // 2.2 根据itemId获取物品的所有信息
        SecondHandItem item = secondHandItemService.getOneById(itemId);
        // 2.3 补全订单中与item有关的属性
        trade.setItemInformation(item.getInformation());
        trade.setItemImage(item.getImage());
        trade.setItemPrice(item.getPrice());
        // 2.4 补全createTime和updateTime
        trade.setCreateTime(LocalDateTime.now());
        trade.setUpdateTime(LocalDateTime.now());
        // 2.5 插入订单信息
        secondHandTradeMapper.insert(trade);
        // 3 创建关联信息
        // 3.1 创建关联类，完善关联类信息
        // 创建状态，所有对象状态均为1 1:创建 2:取消 3:完成 4:删除
        SecondHandTradeUser tradeUser = new SecondHandTradeUser();
        // 3.2 通过订单编号获取订单id 并补全信息
        int tradeId = secondHandTradeMapper.selectIdByNumber(number);
        tradeUser.setSecondHandTradeId(tradeId);
        // 3.3订单的状态设置成1
        tradeUser.setSecondHandTradeStatus(1);
        // 3.4 buyerId和状态1
        tradeUser.setBuyerId(buyerId);
        tradeUser.setBuyerStatus(1);
        // 3.5 sellerId和状态
        tradeUser.setSellerId(item.getUserId());
        tradeUser.setSellerStatus(1);
        // 3.6时间等
        tradeUser.setCreateTime(LocalDateTime.now());
        tradeUser.setUpdateTime(LocalDateTime.now());
        // 3.7 插入数据
        secondHandTradeUserService.insertOne(tradeUser);
    }
}
