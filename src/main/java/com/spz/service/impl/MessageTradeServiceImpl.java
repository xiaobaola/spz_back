package com.spz.service.impl;

import com.spz.entity.message.MessageTrade;
import com.spz.entity.message.MessageTradeDto;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.entity.user.User;
import com.spz.mapper.*;
import com.spz.service.MessageTradeService;
import lombok.extern.slf4j.Slf4j;
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
    private MessageScrapTradeMapper messageScrapTradeMapper;

    @Autowired
    private MessageTradeDtoMapper messageTradeDtoMapper;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Autowired
    private ScrapTradeMapper scrapTradeMapper;

    @Autowired
    private UserMapper userMapper;

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
        // 1.根据userId 获取 scrapTradeIds
        List<ScrapTrade> scrapTrades = scrapTradeMapper.getByUserId(userId);
        // 1.1.根据scrapTradeUpdateTime进行排序 提前排序
        scrapTrades.sort(Comparator.comparing(ScrapTrade::getUpdateTime).reversed());
        //获取信息
        for(ScrapTrade scrapTrade : scrapTrades) {
            // 2.根据scrapTradeId 获取 messageTradeIds
            List<Integer> messageTradeIds = messageScrapTradeMapper.getMessageTradeIdsByScrapTradeId(scrapTrade.getId());
            for (Integer messageTradeId : messageTradeIds) {
                // 3.根据messageTradeIds 获取 messageTrade对象
                MessageTradeDto messageTrade = messageTradeMapper.getById(messageTradeId);
                messageTrade.setNumber(scrapTrade.getNumber());
                messageTrade.setPrice(scrapTrade.getPrice());
                list.add(messageTrade);
            }
        }
        return list;
    }


    @Override
    public List<User> getUserMessage(Integer userId) {
        //排序 可优化
        List<Integer> list1 = new ArrayList<>();
        List<User> list2 = new ArrayList<>();
        List<Integer> userId2ByUserId1 = relationshipMapper.getUserId2ByUserId1(userId, 2);
        List<Integer> userId1ByUserId2 = relationshipMapper.getUserId1ByUserId2(userId, 2);
        list1.addAll(userId1ByUserId2);
        list1.addAll(userId2ByUserId1);
        for (Integer id:list1){
            list2.add(userMapper.getUserById(id));
        }
        return list2;
    }


}
