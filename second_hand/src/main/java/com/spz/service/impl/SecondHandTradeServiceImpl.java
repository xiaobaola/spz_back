package com.spz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.spz.entity.SecondHandItem;
import com.spz.entity.SecondHandTrade;
import com.spz.entity.dto.SecondHandTradeDto;
import com.spz.entity.SecondHandTradeUser;
import com.spz.entity.User;
import com.spz.mapper.SecondHandTradeMapper;
import com.spz.service.SecondHandItemService;
import com.spz.service.SecondHandTradeService;
import com.spz.service.SecondHandTradeUserService;
import com.spz.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecondHandTradeServiceImpl implements SecondHandTradeService {

    private SecondHandTradeMapper tradeMapper;

    private SecondHandItemService itemService;

    private SecondHandTradeUserService tradeUserService;

    private UserService userService;
    @Autowired
    public void setTradeMapper(SecondHandTradeMapper tradeMapper) {
        this.tradeMapper = tradeMapper;
    }
    @Autowired
    public void setItemService(SecondHandItemService itemService) {
        this.itemService = itemService;
    }
    @Autowired
    public void setTradeUserService(SecondHandTradeUserService tradeUserService) {
        this.tradeUserService = tradeUserService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<SecondHandTrade> getTradeByBuyerId(int buyerId) {
        // 获取买家的二手交易订单
        // 1通过关联表找到tradeId
        List<SecondHandTrade> secondHandTrades = new ArrayList<>();
        for (SecondHandTradeUser tradeUser : tradeUserService.getSomeByBuyerId(buyerId)) {
            int tradeId = tradeUser.getSecondHandTradeId();
            SecondHandTrade trade = tradeMapper.selectOneById(tradeId);
            secondHandTrades.add(trade);
        }

        // 2 通过tradeId拿到trade的信息
        return secondHandTrades;
    }

    @Override
    public void addByBuyerIdAndItemIdAndTrade(int buyerId, int itemId, SecondHandTrade trade) {
        // 1物品状态变为下架状态 2->3
        // 可以优化 防止多次购物，加锁，加条件判断
        itemService.changeStatusById(3,itemId);
        // 2 创建二手交易订单
        // 2.1生成订单的编号
        long orderId = IdWorker.getId();
        String number = String.valueOf(orderId);
        // 补全
        trade.setNumber(number);
        // 2.2 根据itemId获取物品的所有信息
        SecondHandItem item = itemService.getOneById(itemId);
        // 2.3 补全订单中与item有关的属性
        trade.setItemInformation(item.getInformation());
        trade.setItemImage(item.getImage());
        trade.setItemPrice(item.getPrice());
        // 2.4 补全createTime和updateTime
        trade.setCreateTime(LocalDateTime.now());
        trade.setUpdateTime(LocalDateTime.now());
        // 2.5 插入订单信息
        tradeMapper.insert(trade);
        // 3 创建关联信息
        // 3.1 创建关联类，完善关联类信息
        // 创建状态，所有对象状态均为1 1:创建 2:取消 3:完成 4:删除
        SecondHandTradeUser tradeUser = new SecondHandTradeUser();
        // 3.2 通过订单编号获取订单id 并补全信息
        int tradeId = tradeMapper.selectIdByNumber(number);
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
        tradeUserService.add(tradeUser);
    }


    @Override
    public List<SecondHandTradeDto> getTradeDtoListByBuyerId(int buyerId) {
        List<SecondHandTradeUser> tradeUsers = tradeUserService.getSomeByBuyerId(buyerId);
        // 抽成一个方法
        return getTradeDtosByTradeUsers(tradeUsers);
    }

    @Override
    public List<SecondHandTradeDto> getTradeDtoListBySellerId(int sellerId) {
        List<SecondHandTradeUser> tradeUsers = tradeUserService.getSomeBySellerId(sellerId);
        // 同理
        return getTradeDtosByTradeUsers(tradeUsers);
    }

    @Override
    public void changeBuyerTradeBuyerStatusByTradeId(int tradeId) {
        // 1改变关联表的状态信息 无需对订单表进行更改
        // 订单状态 创建态1 -> 取消态2 谁取消修改关联表中谁的状态值
        int buyerStatus = 2;
        // 2根据tradeId获取订单关联表的数据 如果要进行安全优化的话
        // 3修改关联表的数据
        tradeUserService.changeBuyerStatusByTradeId(buyerStatus,tradeId);
    }

    @Override
    public void changeSellerTradeSellerStatusByTradeId(int tradeId) {
        int sellerStatus = 2;
        tradeUserService.changeSellerStatusByTradeId(sellerStatus,tradeId);
    }

    private List<SecondHandTradeDto> getTradeDtosByTradeUsers(List<SecondHandTradeUser> tradeUsers) {
        // 1通过buyerId获取 关系信息
        List<SecondHandTradeDto> secondHandTradeDtos = new ArrayList<>();
//        log.info("{}",tradeUsers);
        // 2遍历tadeUsers每一个tadeUser找到对应的trade,seller
        for(SecondHandTradeUser tradeUser : tradeUsers) {
            // 2.1 创建dto
            SecondHandTradeDto tradeDto = new SecondHandTradeDto();
            // 2.2 通过tradeId获取trade
            SecondHandTrade trade = tradeMapper.selectOneById(tradeUser.getSecondHandTradeId());
            // 2.3 对象拷贝trade拷贝到tradeDto
            BeanUtils.copyProperties(trade,tradeDto);
            // 2.4 通过sellerId获取seller
            User seller = userService.getById(tradeUser.getSellerId());
            // 2.5 补全tradeDto的seller信息
            tradeDto.setSellerImage(seller.getImage());
            tradeDto.setSellerUsername(seller.getUsername());
            tradeDto.setSellerStatus(tradeUser.getSellerStatus());
            // 2.6 通过buyerId获取buyer
            User buyer = userService.getById(tradeUser.getBuyerId());
            tradeDto.setBuyerImage(buyer.getImage());
            tradeDto.setBuyerUsername(buyer.getUsername());
            tradeDto.setBuyerStatus(tradeUser.getSellerStatus());
            // 2.7设置tradeStatus
            tradeDto.setTradeStatus(tradeUser.getSecondHandTradeStatus());
            // 2.8 加入到list中
            secondHandTradeDtos.add(tradeDto);
        }
        return secondHandTradeDtos;
    }
}
