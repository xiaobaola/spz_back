package com.spz.service.impl;

import com.spz.entity.user.UserMessage;
import com.spz.mapper.UserMessageMapper;
import com.spz.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public UserMessage getById(UserMessage userMessage) {
        return userMessageMapper.getById(userMessage);
    }

    @Override
    public UserMessage getByInfo(UserMessage userMessage) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMessageMapper.getByInfo(userMessage);
    }

    @Override
    public void updeteById(UserMessage userMessage) {
        userMessage.setUpdateTime(LocalDateTime.now());

    }
}
