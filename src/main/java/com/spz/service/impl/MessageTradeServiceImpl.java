package com.spz.service.impl;

import com.spz.entity.communicate.MessageTrade;
import com.spz.mapper.MessageTradeMapper;
import com.spz.service.ManagerService;
import com.spz.service.MessageTradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MessageTradeServiceImpl implements MessageTradeService {

    @Autowired
   private MessageTradeMapper messageTradeMapper;

    @Override
    public void insert3(MessageTrade messageTrade) {
        messageTrade.setCreateTime(LocalDateTime.now());
        messageTrade.setUpdateTime(LocalDateTime.now());
        messageTradeMapper.insert3(messageTrade);
    }

    @Override
    public List<MessageTrade> list3() {
        return messageTradeMapper.list3();
    }

    @Override
    public MessageTrade getById(Integer id) {
        return messageTradeMapper.getById(id);
    }
}
