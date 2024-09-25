package com.spz.personal_extend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.entity.page.PageBean;
import com.spz.personal_extend.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface UserService extends IService<User> {

    User getByUsernameAndPassword(String username, String password);

    void changeById(User user);

    PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end);

    User getById(Integer id);

    void insertUser(User user);
    
    ArrayList<User> getList();

    List<User> getByLikeUsername(String info);

    User getByPhone(String info);

    List<User> getListByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end);

    List<User> getUsersByUserIds(List<Integer> userId2s);

    User wxLogin(String code);
}
