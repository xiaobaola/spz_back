package com.spz.service;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MessageScrapTradeService {

    List<Integer> getMessageTradeIdById(Integer id);


    List<Integer> getScrapTradeIdById(Integer id);

    void insertByid(List<Integer> scrapTradeIds,Integer messageTradeId);

    Integer getTotalByMessageScrapTrade(Integer userId);
}
