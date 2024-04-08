package com.spz.mapper;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageScrapTradeMapper {
    @Select("select message_trade_id from message_scrap_trade where scrap_trade_id=#{id}")
    List<Integer> getMessageTradeIdById(Integer id);

    @Select("select scrap_trade_id from message_scrap_trade where id=#{id}")
    List<MessageScrapTrade> getScrapTradeIdById(Integer id);

}
