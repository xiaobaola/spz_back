package com.spz.service;

import com.spz.entity.communicate.MessageTrade;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageTradeService {

    void insert3(MessageTrade messageTrade);


    List<MessageTrade> list3();

    MessageTrade getById(Integer id);
}
