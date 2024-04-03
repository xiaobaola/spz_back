package com.spz.mapper;

import com.spz.entity.scrap.ScrapTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ScrapTradeMapper {
    List<ScrapTrade> list(String number, LocalDateTime begin, LocalDateTime end);

    @Insert("insert into scrap_trade(number, user_id, consignee, address, phone, price, image, update_time, create_time) " +
            "VALUES (#{number}, #{userId}, #{consignee}, #{address}, #{phone}, #{price}, #{image}, #{updateTime}, #{createTime})")
    void insert(ScrapTrade scrapTrade);

    @Select("select * from scrap_trade where number=#{number}")
    ScrapTrade getByNumber(ScrapTrade scrapTrade);
}
