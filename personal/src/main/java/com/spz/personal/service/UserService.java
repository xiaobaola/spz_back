package com.spz.personal.service;

import com.spz.public_resouce.entity.page.PageBean;
import com.spz.personal.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface UserService {

    User getByUsernameAndPassword(String username, String password);

    void changeById(User user);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    User getById(Integer id);

    void insert(User user);
    
    ArrayList<User> getList();

    List<User> getByLikeUsername(String info);

    User getByPhone(String info);

    List<User> getListByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end);

    List<User> getUsersByUserIds(List<Integer> userId2s);
}
