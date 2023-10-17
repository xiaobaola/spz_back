package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.user.UserMessage;

import java.time.LocalDate;

public interface UserMessageService {

    public UserMessage getById(UserMessage userMessage);

    UserMessage getByInfo(UserMessage userMessage);

    void updeteById(UserMessage userMessage);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    UserMessage getByIdNumber(Integer id);

    void insert(UserMessage userMessage);
}
