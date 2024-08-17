package com.spz.personal.service;

import com.spz.personal.entity.User;

public interface UserRegisterService {
    void userRegister(User user);

    Integer getIdByUserName(String username);
}
