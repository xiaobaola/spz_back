package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.page.PageBean;
import com.spz.entity.user.User;
import com.spz.mapper.UserMapper;
import com.spz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getById(User user) {
        return userMapper.getById(user);
    }

    @Override
    public User getByInfo(User user) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMapper.getByInfo(user);
    }

    @Override
    public void updeteById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<User> userList = userMapper.list(username, begin, end);

        //为了获取total
        Page<User> userPage = (Page<User>) userList;

        //3、封装pageBean对象
        PageBean pageBean = new PageBean(userPage.getTotal(), userPage.getResult());

        return pageBean;
    }

    @Override
    public User getByIdNumber(Integer id) {
        return userMapper.getByIdNumber(id);
    }

    @Override
    public void insert(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }
}
