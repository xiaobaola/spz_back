package com.spz.communication.service.impl;

import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.message.dto.MessageTradeDto;
import com.spz.communication.service.MessageScrapTradeService;
import com.spz.communication.service.RelationshipService;
import com.spz.personal.service.UserService;
import com.spz.recycle.entity.ScrapTrade;
import com.spz.personal.entity.User;
import com.spz.communication.mapper.MessageTradeMapper;
import com.spz.communication.service.MessageTradeService;
import com.spz.recycle.service.ScrapTradeService;
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
    private MessageScrapTradeService messageScrapTradeService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private ScrapTradeService scrapTradeService;

    @Autowired
    private UserService userService;

    @Override
    public void add(MessageTrade messageTrade) {
//        log.info(messageTrade.toString());
        messageTrade.setCreateTime(LocalDateTime.now());
        messageTrade.setUpdateTime(LocalDateTime.now());
        messageTradeMapper.insert(messageTrade);
    }

    @Override
    public List<MessageTrade> getList() {
        return messageTradeMapper.selectList();
    }

    @Override
    public MessageTrade getById(Integer id) {
        return messageTradeMapper.getById(id);
    }

    @Override
    public List<MessageTradeDto> getMessageTradeDtosByUserId(Integer userId) {
        List<MessageTradeDto> list = new ArrayList<>();
        // 1.根据userId 获取 scrapTradeIds
        List<ScrapTrade> scrapTrades = scrapTradeService.getListByUserId(userId);
        // 1.1.根据scrapTradeUpdateTime进行排序 提前排序
        scrapTrades.sort(Comparator.comparing(ScrapTrade::getUpdateTime).reversed());
        //获取信息
        for(ScrapTrade scrapTrade : scrapTrades) {
            // 2.根据scrapTradeId 获取 messageTradeIds
            List<Integer> messageTradeIds = messageScrapTradeService.getMessageTradeIdsByScrapTradeId(scrapTrade.getId());
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
    public List<User> getUsersByUserId(Integer userId) {
        //排序 可优化
        List<User> list2 = new ArrayList<>();
        List<Integer> userId2ByUserId1 = relationshipService.getUserId2sByUserId1AndStatus(userId, 2);
        List<Integer> list1 = new ArrayList<>(userId2ByUserId1);
        for (Integer id:list1){
            list2.add(userService.getById(id));
        }
        return list2;
    }


}
