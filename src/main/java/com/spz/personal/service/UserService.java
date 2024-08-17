package com.spz.personal.service;

import com.spz.personal.entity.UserDto;
import com.spz.public_resource.entity.page.PageBean;
import com.spz.personal.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User getByUsernameAndPassword(User user);

    void updeteById(User user);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    User getById(Integer id);

    void insert(User user);

    List<UserDto> getUserDtoListByInfo(String info, Integer userId);

    List<UserDto> getUserDtoListByUserId(Integer userId);
}
