package com.spz.mapper;

import com.spz.entity.scrap.ScrapTrade;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScrapTradeMapper {
    List<ScrapTrade> list(String number, LocalDate begin, LocalDate end);
}
