package com.spz.service;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MessageScrapTradeService {

    List<Integer> getMessageTradeIdById(Integer id);


    List<MessageScrapTrade> getScrapTradeIdById(Integer id);

}
