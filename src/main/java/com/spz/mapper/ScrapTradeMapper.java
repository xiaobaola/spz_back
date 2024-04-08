package com.spz.mapper;

import com.spz.entity.scrap.ScrapTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update("update scrap_trade set status=#{status} where id=#{id}")
    void updateStatus(ScrapTrade scrapTrade);

    @Select("select * from scrap_trade where user_id=#{userId}")
    List<ScrapTrade> getNumberByUserId(Integer userId);

    @Select("select user_id from scrap_trade where id=#{id}")
    Integer getUserIdById(Integer id);
}
