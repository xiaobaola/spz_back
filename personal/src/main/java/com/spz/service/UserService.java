package com.spz.service;

import com.spz.entity.user.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    User getByUsernameAndPassword(String username,String password);

    void updateById(User user);

    User getById(Integer id);

    void insert(User user);

    List<User> list(String username, LocalDate begin, LocalDate end);

    List<User> getByLikeUsername(String likeUsername);

    User getByPhone(String info);

    List<User> getUsersByUserIds(List<Integer> userIds);
}
