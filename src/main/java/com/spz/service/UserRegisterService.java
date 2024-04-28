package com.spz.service;

import com.spz.entity.user.User;

public interface UserRegisterService {
    void userRegister(User user);

    Integer getIdByUserName(User user);
}
