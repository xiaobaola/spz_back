package com.spz.service.impl;

import com.spz.entity.user.User;
import com.spz.mapper.ChangeMapper;
import com.spz.service.ChangeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChangeUserServiceImpl implements ChangeUserService {

    @Autowired
    private ChangeMapper changeMapper;


    @Override
    public void changeUserName(Integer id, String username) {
       changeMapper.changeUserName(id,username);
    }

    @Override
    public void changePhone(Integer id, String phone) {
        changeMapper.changePhone(id,phone);
    }

    @Override
    public void changePassword(Integer id, String password) {
        changeMapper.changePassword(id,password);
    }
}
