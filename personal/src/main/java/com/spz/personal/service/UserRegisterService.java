package com.spz.personal.service;

import com.spz.personal.entity.User;

public interface UserRegisterService {
    void add(User user);

    Integer getIdByUserName(String username);
}
