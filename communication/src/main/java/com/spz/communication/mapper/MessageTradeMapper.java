package com.spz.communication.mapper;

import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.dto.MessageTradeDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageTradeMapper {
    @Insert("insert into message_trade(name, message, trade_time_start, trade_time_finish ,create_time, update_time) " +
            "values (#{name}, #{message}, #{tradeTimeStart}, #{tradeTimeFinish} ,#{createTime} ,#{updateTime})")
    void insert(MessageTrade messageTrade);

    @Select("select * from message_trade order by update_time desc")
    List<MessageTrade> selectList();

    @Select("select * from message_trade where id=#{id}")
    MessageTradeDto selectById(Integer id);

    @Select("select * from message_trade where id=#{userId};")
    List<MessageTradeDto> selectByUserId(Integer userId);
}
