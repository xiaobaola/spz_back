package com.spz.mapper;

import com.spz.entity.secondhand.SecondHandTradeUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecondHandTradeUserMapper {
    @Insert("insert into second_hand_trade_user(second_hand_trade_id, second_hand_trade_status, " +
            "buyer_id, buyer_status, seller_id, seller_status, create_time, update_time) " +
            "VALUES (#{secondHandTradeId},#{secondHandTradeStatus},#{buyerId},#{buyerStatus}," +
            "#{sellerId},#{sellerStatus},#{createTime},#{updateTime})")
    void insertOne(SecondHandTradeUser tradeUser);
}
