package com.spz.service.impl;

import com.spz.entity.scrap.ScrapTrade;
import com.spz.mapper.MessageScrapTradeMapper;
import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.MessageScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
                messageScrapTradeMapper.insertById(messageTradeId,element.getId(),0);
                element.setStatus(1);
                scrapTradeMapper.updateStatus(element);
            }
        }
    }

    @Override
    public Integer getTotalByMessageScrapTrade(Integer userId) {
        // 获取 scrapTradeIds
        List<Integer> scrapTradeIds = scrapTradeMapper.getIdByUserId(userId);
        // 根据 scrapTradeId 统计status
        Integer total = messageScrapTradeMapper.getStatusCountByScrapTradeIds(scrapTradeIds, 0);
        log.info("total: {}", total);
        return total;
    }

    @Override
    public void updateStatusByUserId(Integer userId) {

    }

}
