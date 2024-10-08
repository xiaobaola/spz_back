package com.spz.personal.service.impl;

import com.spz.personal.mapper.UserChangeMapper;
import com.spz.personal.service.UserChangeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUserChangeUserServiceImpl implements UserChangeUserService {


    private UserChangeMapper userChangeMapper;

    @Autowired
    public void setChangeMapper(UserChangeMapper userChangeMapper) {
        this.userChangeMapper = userChangeMapper;
    }

    @Override
    public void changeUserName(Integer id, String username) {
       userChangeMapper.changeUserName(id,username);
    }

    @Override
    public void changePhone(Integer id, String phone) {
        userChangeMapper.changePhone(id,phone);
    }

    @Override
    public void changePassword(Integer id, String password) {
        userChangeMapper.changePassword(id,password);
    }
}
