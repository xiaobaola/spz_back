package com.spz.service;

import com.spz.entity.communicate.MessageTrade;
import com.spz.entity.communicate.MessageTradeDto;
import com.spz.entity.user.UserMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MessageTradeService {

    void insert3(MessageTrade messageTrade);


    List<MessageTrade> list3();

    MessageTrade getById(Integer id);

    List<MessageTradeDto> getByUserId(Integer userId);

    Integer getTotalByMessageScrapTrade(Integer userId);

    List<UserMessage> getUserMessage(Integer userId);
}
