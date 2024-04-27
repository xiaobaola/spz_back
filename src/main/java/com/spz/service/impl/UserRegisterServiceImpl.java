package com.spz.service.impl;

import com.spz.entity.user.User;
import com.spz.mapper.UserMapper;
import com.spz.mapper.UserRegisterMapper;
import com.spz.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRegisterMapper userRegisterMapper;

    @Override
    public void userRegister(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userRegisterMapper.userRegister(user);
    }
}
