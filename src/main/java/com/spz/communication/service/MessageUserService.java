package com.spz.communication.service;

import com.spz.communication.entity.message.MessageUser;

import java.util.List;

public interface MessageUserService {
    List<MessageUser> list(Integer userId1, Integer userId2);

    void insert(MessageUser messageUser);
}
