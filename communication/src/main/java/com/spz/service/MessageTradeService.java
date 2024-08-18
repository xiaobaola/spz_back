package com.spz.service;

import com.spz.entity.message.MessageTrade;
import com.spz.entity.dto.MessageTradeDto;
import com.spz.entity.User;

import java.util.List;

public interface MessageTradeService {

    void add(MessageTrade messageTrade);


    List<MessageTrade> getList();

    MessageTrade getById(Integer id);

    List<MessageTradeDto> getMessageTradeDtosByUserId(Integer userId);

    List<User> getUsersByUserId(Integer userId);
}
