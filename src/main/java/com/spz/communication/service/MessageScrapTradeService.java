package com.spz.communication.service;

import java.util.List;


public interface MessageScrapTradeService {

    List<Integer> getMessageTradeIdById(Integer id);


    List<Integer> getScrapTradeIdById(Integer id);

    void insertByid(List<Integer> scrapTradeIds,Integer messageTradeId);

    Integer getTotalByMessageScrapTrade(Integer userId);

    void updateStatusByUserId(Integer userId);
}
