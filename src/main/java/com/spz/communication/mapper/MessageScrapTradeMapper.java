package com.spz.communication.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageScrapTradeMapper {
    @Select("select message_trade_id from message_scrap_trade where scrap_trade_id=#{userId}")
    List<Integer> getMessageTradeIdById(Integer userId);

    @Select("select scrap_trade_id from message_scrap_trade where id=#{userId}")
    List<Integer> getScrapTradeIdById(Integer userId);

    @Select("select count(status) from message_scrap_trade where status=#{status} and scrap_trade_id=#{userId}")
    Integer getCountBystatus(Integer status,Integer userId);

    @Insert("insert into message_scrap_trade(message_trade_id, scrap_trade_id,status) values (#{messageTradeId},#{scrapTradeId},#{status})")
    void insertById(Integer messageTradeId, Integer scrapTradeId, Integer status);

    Integer getStatusCountByScrapTradeIdsAndStatus(List<Integer> scrapTradeIds, Integer status);

    @Update("update message_scrap_trade set status = #{status} where scrap_trade_id = #{id}")
    void updateStatusByScrapTradeId(Integer id, Integer status);

    @Select("select message_trade_id from message_scrap_trade where scrap_trade_id=#{id}")
    List<Integer> getMessageTradeIdsByScrapTradeId(Integer id);

//    void updateStatusByScrapTradeIdsAndStatus(List<Integer> scrapTradeIds, Integer status);
}
