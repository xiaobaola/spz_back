package com.spz.service.impl;

import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.service.MessageScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageScrapTradeServiceImpl implements MessageScrapTradeService {
    @Autowired
    MessageScrapTradeMapper messageScrapTradeMapper;


    @Override
    public MessageScrapTrade getMessageTradeIdById(Integer id) {
        return messageScrapTradeMapper.getMessageTradeIdById(id);
    }

    @Override
    public MessageScrapTrade getScrapTradeIdById(Integer id) {
        return messageScrapTradeMapper.getScrapTradeIdById(id);
    }
}
