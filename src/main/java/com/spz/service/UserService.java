package com.spz.service;

import com.spz.entity.dto.UserDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.user.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    public User getById(User user);

    User getByUsernameAndPassword(User user);

    void updeteById(User user);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    User getByIdNumber(Integer id);

    void insert(User user);

    List<UserDto> getUserDtoListByInfo(String info, Integer userId);
}
