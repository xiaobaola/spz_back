package com.spz.recycle.mapper;

import com.spz.recycle.entity.ScrapTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface ScrapTradeMapper {
    List<ScrapTrade> selectList(String number, Integer status, Date begin, Date end);

    @Insert("insert into scrap_trade(number, user_id, consignee, address, phone, price, image, update_time, create_time) " +
            "VALUES (#{number}, #{userId}, #{consignee}, #{address}, #{phone}, #{price}, #{image}, #{updateTime}, #{createTime})")
    void insert(ScrapTrade scrapTrade);

    @Select("select * from scrap_trade where number=#{number}")
    ScrapTrade selectByNumber(ScrapTrade scrapTrade);

    @Update("update scrap_trade set status=#{status} where id=#{id}")
    void updateStatus(ScrapTrade scrapTrade);

    @Select("select * from scrap_trade where id=#{id}")
    ScrapTrade selectById(Integer id);

    @Select("select * from scrap_trade where user_id=#{userId}")
    List<ScrapTrade> selectListByUserId(Integer userId);


    @Select("select id from scrap_trade where user_id=#{userId}")
    List<Integer> selectIdsByUserId(Integer userId);

    @Update("update scrap_trade set status=3 where id=#{id}")
    void updateStatusById(Integer id);
}
