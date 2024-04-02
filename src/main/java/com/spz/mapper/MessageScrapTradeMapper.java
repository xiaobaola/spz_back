package com.spz.mapper;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MessageScrapTradeMapper {
    @Select("select message_trade_id from message_scrap_trade where id=#{id}")
    MessageScrapTrade getMessageTradeIdById(Integer id);

    @Select("select scrap_trade_id from message_scrap_trade where id=#{id}")
    MessageScrapTrade getScrapTradeIdById(Integer id);

}
