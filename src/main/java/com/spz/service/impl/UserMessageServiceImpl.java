package com.spz.service.impl;

import com.spz.entity.user.UserMessage;
import com.spz.mapper.UserMessageMapper;
import com.spz.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public UserMessage getById(UserMessage userMessage) {
        return userMessageMapper.getById(userMessage);
    }
}
