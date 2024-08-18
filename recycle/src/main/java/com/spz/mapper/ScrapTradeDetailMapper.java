package com.spz.mapper;

import com.spz.entity.ScrapTradeDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScrapTradeDetailMapper {
    @Insert("insert into scrap_trade_detail(user_id, scrap_id, scrap_trade_id, count, price, update_time, create_time) " +
            "VALUES(#{userId}, #{scrapId}, #{scrapTradeId}, #{count}, #{price}, #{updateTime}, #{createTime}) ")
    void insert(ScrapTradeDetail item);
//    void insertList(List<ScrapTradeDetail> scrapTradeDetails);
}
