package com.spz.personal.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.page.PageBean;
import com.spz.personal.entity.User;
import com.spz.personal.mapper.UserMapper;
import com.spz.personal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public User getByUsernameAndPassword(String username,String password) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMapper.selectByUsernameAndPassword(username,password);
    }

    @Override
    public void changeById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<User> userList = userMapper.selectByUsernameOrBeginAndEnd(username, begin, end);

        //为了获取total
        Page<User> userPage = (Page<User>) userList;

        //3、封装pageBean对象
        return new PageBean(userPage.getTotal(), userPage.getResult());
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void insert(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public ArrayList<User> getList() {
        return userMapper.selectByAll();
    }

    @Override
    public List<User> getByLikeUsername(String info) {
        return userMapper.selectListByLikeUsername(info);
    }

    @Override
    public User getByPhone(String info) {
        return userMapper.selectByPhone(info);
    }

    @Override
    public List<User> getListByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end) {
        return userMapper.selectByUsernameOrBeginAndEnd(username, begin, end);
    }

    @Override
    public List<User> getUsersByUserIds(List<Integer> userId2s) {
        return userMapper.selectUsersByUserIds(userId2s);
    }
}
