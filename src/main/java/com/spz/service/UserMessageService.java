package com.spz.service;

import com.spz.entity.user.UserMessage;

public interface UserMessageService {

    public UserMessage getById(UserMessage userMessage);

    UserMessage getByInfo(UserMessage userMessage);

    void updeteById(UserMessage userMessage);
}
