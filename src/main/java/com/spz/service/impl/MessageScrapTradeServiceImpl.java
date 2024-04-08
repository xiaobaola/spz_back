package com.spz.service.impl;

import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.service.MessageScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageScrapTradeServiceImpl implements MessageScrapTradeService {
    @Autowired
    MessageScrapTradeMapper messageScrapTradeMapper;


    @Override
    public List<Integer> getMessageTradeIdById(Integer id) {
        return messageScrapTradeMapper.getMessageTradeIdById(id);
    }

    @Override
    public List<MessageScrapTrade> getScrapTradeIdById(Integer id) {
        return messageScrapTradeMapper.getScrapTradeIdById(id);
    }
}
