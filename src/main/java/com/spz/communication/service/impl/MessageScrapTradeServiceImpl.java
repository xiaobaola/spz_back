package com.spz.communication.service.impl;

import com.spz.recycle.entity.ScrapTrade;
import com.spz.communication.mapper.MessageScrapTradeMapper;
import com.spz.recycle.mapper.ScrapTradeMapper;
import com.spz.communication.service.MessageScrapTradeService;
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
        List<Integer> scrapTradeIds = scrapTradeMapper.getIdsByUserId(userId);
        if(scrapTradeIds.isEmpty()) {
            log.info("total: {}", 0);
            return 0;
        }
        // 根据 scrapTradeId 统计status
        Integer total = messageScrapTradeMapper.getStatusCountByScrapTradeIdsAndStatus(scrapTradeIds, 0);
        log.info("total: {}", total);
        return total;
    }

    @Override
    public void updateStatusByUserId(Integer userId) {
        // 1.根据userId 获取 scrapTradeIds
        List<Integer> scrapTradeIds = scrapTradeMapper.getIdsByUserId(userId);
        // 2.根据ids 更新 关联表的status
        for(Integer scrapTradeId : scrapTradeIds) {
            messageScrapTradeMapper.updateStatusByScrapTradeId(scrapTradeId, 1);
        }
//        messageScrapTradeMapper.updateStatusByScrapTradeIdsAndStatus(scrapTradeIds, 1);
    }

}
