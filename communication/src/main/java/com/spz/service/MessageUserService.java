package com.spz.service;

import com.spz.entity.message.MessageUser;

import java.util.List;

public interface MessageUserService {
    List<MessageUser> getList(Integer userId1, Integer userId2);

    void add(MessageUser messageUser);
}
