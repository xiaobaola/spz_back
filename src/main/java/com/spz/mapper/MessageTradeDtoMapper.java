package com.spz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MessageTradeDtoMapper {
    @Select("select number from scrap_trade where id=#{Id};")
    String selectNumberByMessageTradeId(Integer Id);

    @Select("select price from scrap_trade where id=#{Id};")
    Integer selectPriceByMessageTradeId(Integer Id);
}
