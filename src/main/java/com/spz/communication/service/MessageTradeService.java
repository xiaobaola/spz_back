package com.spz.communication.service;

import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.message.dto.MessageTradeDto;
import com.spz.personal.entity.User;

import java.util.List;

public interface MessageTradeService {

    void insert3(MessageTrade messageTrade);


    List<MessageTrade> list3();

    MessageTrade getById(Integer id);

    List<MessageTradeDto> getByUserId(Integer userId);

    List<User> getUserMessage(Integer userId);
}
