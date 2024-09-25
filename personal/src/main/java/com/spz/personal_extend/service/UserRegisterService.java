package com.spz.personal_extend.service;

import com.spz.personal_extend.entity.User;

public interface UserRegisterService {
    void add(User user);

    Integer getIdByUserName(String username);
}
