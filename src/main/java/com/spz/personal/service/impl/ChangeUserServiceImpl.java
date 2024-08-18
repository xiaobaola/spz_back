package com.spz.personal.service.impl;

import com.spz.personal.mapper.ChangeMapper;
import com.spz.personal.service.ChangeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangeUserServiceImpl implements ChangeUserService {


    private ChangeMapper changeMapper;

    @Autowired
    public void setChangeMapper(ChangeMapper changeMapper) {
        this.changeMapper = changeMapper;
    }

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
