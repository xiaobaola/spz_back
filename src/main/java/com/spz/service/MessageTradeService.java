package com.spz.service;

import com.spz.entity.message.MessageTrade;
import com.spz.entity.message.MessageTradeDto;
import com.spz.entity.user.User;

import java.util.List;

public interface MessageTradeService {

    void insert3(MessageTrade messageTrade);


    List<MessageTrade> list3();

    MessageTrade getById(Integer id);

    List<MessageTradeDto> getByUserId(Integer userId);

    List<User> getUserMessage(Integer userId);
}
