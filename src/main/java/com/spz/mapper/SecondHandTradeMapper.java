package com.spz.mapper;

import com.spz.entity.secondhand.SecondHandTrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecondHandTradeMapper {
    @Select("select * from second_hand_trade where buyer_id = #{buyerId}")
    List<SecondHandTrade> selectByBuyerId(int buyerId);
}
