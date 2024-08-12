package com.spz.mapper;

import com.spz.entity.secondhand.SecondHandTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecondHandTradeMapper {
    @Select("select * from second_hand_trade where buyer_id = #{buyerId}")
    List<SecondHandTrade> selectByBuyerId(int buyerId);

    @Insert("insert into second_hand_trade(number, item_image, item_price, item_information, place, approach, " +
            "trade_time, create_time, update_time) values (#{number},#{itemImage},#{itemPrice},#{itemInformation}," +
            "#{place},#{approach},#{tradeTime},#{createTime},#{updateTime})")
    void insert(SecondHandTrade trade);

    @Select("select id from second_hand_trade where number=#{number}")
    int selectIdByNumber(String number);
}
