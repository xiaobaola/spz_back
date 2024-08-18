package com.spz.communication.service;

import java.util.List;


public interface MessageScrapTradeService {

    List<Integer> getMessageTradeIdById(Integer id);


    List<Integer> getScrapTradeIdById(Integer id);

    void addById(List<Integer> scrapTradeIds, Integer messageTradeId);

    Integer getTotalByMessageScrapTrade(Integer userId);

    void changeStatusByUserId(Integer userId);

    List<Integer> getMessageTradeIdsByScrapTradeId(Integer id);
}
