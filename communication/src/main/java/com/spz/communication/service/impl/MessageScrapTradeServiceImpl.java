package com.spz.communication.service.impl;

import com.spz.recycle.entity.ScrapTrade;
import com.spz.communication.mapper.MessageScrapTradeMapper;
import com.spz.communication.service.MessageScrapTradeService;
import com.spz.recycle.service.ScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageScrapTradeServiceImpl implements MessageScrapTradeService {

    private MessageScrapTradeMapper messageScrapTradeMapper;
    private ScrapTradeService scrapTradeService;

    @Autowired
    public void setMessageScrapTradeMapper(MessageScrapTradeMapper messageScrapTradeMapper) {
        this.messageScrapTradeMapper = messageScrapTradeMapper;
    }

    @Autowired
    public void setScrapTradeService(ScrapTradeService scrapTradeService) {
        this.scrapTradeService = scrapTradeService;
    }




    @Override
    public List<Integer> getMessageTradeIdById(Integer id) {
        return messageScrapTradeMapper.selectMessageTradeIdsByUserId(id);
    }

    @Override
    public List<Integer> getScrapTradeIdById(Integer id) {
        return messageScrapTradeMapper.selectScrapTradeIdsByUserId(id);
    }

    @Override
    public void addById(List<Integer> scrapTradeIds, Integer messageTradeId) {
        List<ScrapTrade> list1 = new ArrayList<>();
        for (Integer scrapTradeId : scrapTradeIds){
            ScrapTrade scrapTradeMapperById = scrapTradeService.getById(scrapTradeId);
            list1.add(scrapTradeMapperById);
        }
        for (ScrapTrade element:list1){
            if(element.getStatus() == 0){
                messageScrapTradeMapper.insert(messageTradeId,element.getId(),0);
                element.setStatus(1);
                scrapTradeService.changeStatus(element);
            }
        }
    }

    @Override
    public Integer getTotalByMessageScrapTrade(Integer userId) {
        // 获取 scrapTradeIds
        List<Integer> scrapTradeIds = scrapTradeService.getIdsByUserId(userId);
        if(scrapTradeIds.isEmpty()) {
            log.info("total: {}", 0);
            return 0;
        }
        // 根据 scrapTradeId 统计status
        Integer total = messageScrapTradeMapper.selectStatusCountByScrapTradeIdsAndStatus(scrapTradeIds, 0);
        log.info("total: {}", total);
        return total;
    }

    @Override
    public void changeStatusByUserId(Integer userId) {
        // 1.根据userId 获取 scrapTradeIds
        List<Integer> scrapTradeIds = scrapTradeService.getIdsByUserId(userId);
        // 2.根据ids 更新 关联表的status
        for(Integer scrapTradeId : scrapTradeIds) {
            messageScrapTradeMapper.updateStatusByScrapTradeId(scrapTradeId, 1);
        }
//        messageScrapTradeMapper.updateStatusByScrapTradeIdsAndStatus(scrapTradeIds, 1);
    }

    @Override
    public List<Integer> getMessageTradeIdsByScrapTradeId(Integer id) {
        return getMessageTradeIdById(id);
    }

}
