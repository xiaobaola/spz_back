package com.spz.communication.service;

import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.dto.MessageTradeDto;
import com.spz.personal_extend.entity.User;

import java.util.List;

public interface MessageTradeService {

    void add(MessageTrade messageTrade);


    List<MessageTrade> getList();

    MessageTrade getById(Integer id);

    List<MessageTradeDto> getMessageTradeDtosByUserId(Integer userId);

    List<User> getUsersByUserId(Integer userId);
}
