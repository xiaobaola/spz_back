package com.spz.mapper;

import com.spz.entity.communicate.MessageTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageTradeMapper {
    @Insert("insert into message_trade(name, message, trade_time_start, trade_time_finish ,create_time, update_time) " +
            "values (#{name}, #{message}, #{tradeTimeStart}, #{tradeTimeFinish} ,#{createTime} ,#{updateTime})")
    void insert3(MessageTrade messageTrade);

    @Select("select * from message_trade")
    List<MessageTrade> list3();

    @Select("select * from message_trade where id=#{id}")
    MessageTrade getById(Integer id);

    @Select("select * from message_trade where id=#{userId};")
    List<MessageTrade> getByUserId(Integer userId);
}
