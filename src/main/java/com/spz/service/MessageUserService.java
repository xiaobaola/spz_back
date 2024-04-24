package com.spz.service;

import com.spz.entity.message.MessageUser;
import com.spz.entity.user.User;

import java.util.List;

public interface MessageUserService {
    List<MessageUser> list(Integer userId1, Integer userId2);

    void insert(MessageUser messageUser);
}
