package com.spz.mapper;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageScrapTradeMapper {
    @Select("select message_trade_id from message_scrap_trade where scrap_trade_id=#{userId}")
    List<Integer> getMessageTradeIdById(Integer userId);

    @Select("select scrap_trade_id from message_scrap_trade where id=#{userId}")
    List<Integer> getScrapTradeIdById(Integer userId);

    @Select("select count(status) from message_scrap_trade where status=#{status} and scrap_trade_id=#{userId}")
    Integer getCountBystatus(Integer status,Integer userId);

}
