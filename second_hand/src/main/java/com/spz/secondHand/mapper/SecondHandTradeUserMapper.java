package com.spz.secondHand.mapper;

import com.spz.secondHand.entity.SecondHandTradeUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SecondHandTradeUserMapper {
    @Insert("insert into second_hand_trade_user(second_hand_trade_id, second_hand_trade_status, " +
            "buyer_id, buyer_status, seller_id, seller_status, create_time, update_time) " +
            "VALUES (#{secondHandTradeId},#{secondHandTradeStatus},#{buyerId},#{buyerStatus}," +
            "#{sellerId},#{sellerStatus},#{createTime},#{updateTime})")
    void insertOne(SecondHandTradeUser tradeUser);

    @Select("select * from second_hand_trade_user where buyer_id=#{buyerId}")
    List<SecondHandTradeUser> selectSomeByBuyerId(int buyerId);

    @Select("select * from second_hand_trade_user where seller_id=#{sellerId}")
    List<SecondHandTradeUser> selectSomeBySellerId(int sellerId);

    @Update("update second_hand_trade_user set buyer_status=#{buyerStatus} where second_hand_trade_id=#{tradeId}")
    void updateBuyerStatusByTradeId(int buyerStatus, int tradeId);

    @Update("update second_hand_trade_user set seller_status=#{sellerStatus} where second_hand_trade_id=#{tradeId}")
    void updateSellerStatusByTradeId(int sellerStatus, int tradeId);
}
