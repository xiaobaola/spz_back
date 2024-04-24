package com.spz.service.impl;

import com.spz.mapper.MessageUserMapper;
import com.spz.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageUserServiceImpl implements MessageUserService {
    @Autowired
    MessageUserMapper messageUserMapper;
}
