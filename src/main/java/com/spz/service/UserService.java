package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.user.User;

import java.time.LocalDate;

public interface UserService {

    public User getById(User user);

    User getByInfo(User user);

    void updeteById(User user);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    User getByIdNumber(Integer id);

    void insert(User user);
}
