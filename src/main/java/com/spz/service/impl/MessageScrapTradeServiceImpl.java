package com.spz.service.impl;

import com.spz.entity.communicate.MessageScrapTrade;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.MessageScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageScrapTradeServiceImpl implements MessageScrapTradeService {
    @Autowired
    private MessageScrapTradeMapper messageScrapTradeMapper;

    @Autowired
    private ScrapTradeMapper scrapTradeMapper;

    @Override
    public List<Integer> getMessageTradeIdById(Integer id) {
        return messageScrapTradeMapper.getMessageTradeIdById(id);
    }

    @Override
    public List<Integer> getScrapTradeIdById(Integer id) {
        return messageScrapTradeMapper.getScrapTradeIdById(id);
    }

    @Override
    public void insertByid(List<Integer> scrapTradeIds, Integer messageTradeId) {
        List<ScrapTrade> list1 = new ArrayList<>();
        for (Integer scrapTradeId : scrapTradeIds){
            ScrapTrade scrapTradeMapperById = scrapTradeMapper.getById(scrapTradeId);
            list1.add(scrapTradeMapperById);
        }
        for (ScrapTrade element:list1){
            if(element.getStatus() == 0){
                messageScrapTradeMapper.insertByid(messageTradeId,element.getId(),0);
                element.setStatus(1);
                scrapTradeMapper.updateStatus(element);
            }
        }
    }

    @Override
    public Integer getTotalByMessageScrapTrade(Integer userId) {
        return null;
    }

    @Override
    public void updateStatusByUserId(Integer userId) {

    }

}
