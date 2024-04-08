package com.spz.service.impl;

import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.entity.communicate.MessageTrade;
import com.spz.entity.user.UserMessage;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.mapper.MessageTradeMapper;
import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.ManagerService;
import com.spz.service.MessageTradeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageTradeServiceImpl implements MessageTradeService {

    @Autowired
   private MessageTradeMapper messageTradeMapper;

    @Autowired
    private ScrapTradeMapper scrapTradeMapper;

    @Autowired
    private MessageScrapTradeMapper messageScrapTradeMapper;

    @Override
    public void insert3(MessageTrade messageTrade) {
//        log.info(messageTrade.toString());
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

    @Override
    public List<MessageTrade> getByUserId(Integer userId) {
        List<MessageTrade> list = new ArrayList<>();
        //获取userid
        Integer userIdById = scrapTradeMapper.getUserIdById(userId);
        //获取message_trade_id
        List<Integer> messageTradeIdById = messageScrapTradeMapper.getMessageTradeIdById(userIdById);
        //获取信息
        for (Integer element: messageTradeIdById){
            MessageTrade messageTradeMapperById = messageTradeMapper.getById(element);
            list.add(messageTradeMapperById);
        }
        return list;
    }
}
