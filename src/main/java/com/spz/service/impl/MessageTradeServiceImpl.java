package com.spz.service.impl;

import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.entity.communicate.MessageTrade;
import com.spz.entity.communicate.MessageTradeDto;
import com.spz.entity.user.UserMessage;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.mapper.MessageTradeDtoMapper;
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
import java.util.Comparator;
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

    @Autowired
    private MessageTradeDtoMapper messageTradeDtoMapper;

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
    public List<MessageTradeDto> getByUserId(Integer userId) {
        List<MessageTradeDto> list = new ArrayList<>();
        List<Integer> messageTradeIdById = messageScrapTradeMapper.getMessageTradeIdById(userId);
        //获取信息
        for (Integer element: messageTradeIdById){
            MessageTradeDto messageTradeMapperById = messageTradeMapper.getById(element);
            messageTradeMapperById.setNumber(messageTradeDtoMapper.selectNumberByMessageTradeId(element));
            messageTradeMapperById.setPrice(messageTradeDtoMapper.selectPriceByMessageTradeId(element));
            list.add(messageTradeMapperById);
        }
        list.sort(Comparator.comparing(MessageTrade::getCreateTime));
        return list;
    }
}
