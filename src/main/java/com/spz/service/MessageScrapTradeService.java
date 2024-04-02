package com.spz.service;

import com.spz.entity.communicate.MessageScrapTrade;
import org.apache.ibatis.annotations.Select;


public interface MessageScrapTradeService {

    MessageScrapTrade getMessageTradeIdById(Integer id);


    MessageScrapTrade getScrapTradeIdById(Integer id);

}
