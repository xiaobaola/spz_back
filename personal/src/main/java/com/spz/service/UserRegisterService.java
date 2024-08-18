package com.spz.service;

import com.spz.entity.User;

public interface UserRegisterService {
    void add(User user);

    Integer getIdByUserName(String username);
}
