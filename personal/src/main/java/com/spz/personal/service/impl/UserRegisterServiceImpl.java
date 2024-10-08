package com.spz.personal.service.impl;

import com.spz.personal.entity.User;
import com.spz.personal.mapper.UserRegisterMapper;
import com.spz.personal.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserRegisterServiceImpl implements UserRegisterService {


    private UserRegisterMapper userRegisterMapper;
    @Autowired
    public void setUserRegisterMapper(UserRegisterMapper userRegisterMapper) {
        this.userRegisterMapper = userRegisterMapper;
    }

    @Override
    public void add(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userRegisterMapper.insert(user);
    }

    @Override
    public Integer getIdByUserName(String username) {
        return userRegisterMapper.selectIdByUserName(username);
    }
}
