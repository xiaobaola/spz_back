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
    public User getByUsernameAndPassword(String username,String password) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMapper.selectByUsernameAndPassword(username,password);
    }

    @Override
    public void updateById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
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
    public List<User> list(String username, LocalDate begin, LocalDate end) {
        return userMapper.selectByUsernameOrBeginAndEnd(username,begin,end);
    }

    @Override
    public List<User> getByLikeUsername(String likeUsername) {
        return userMapper.selectByLikeUsername(likeUsername);
    }

    @Override
    public User getByPhone(String info) {
        return userMapper.selectByPhone(info);
    }

    @Override
    public List<User> getUsersByUserIds(List<Integer> userIds) {
        return userMapper.selectUsersByUserIds(userIds);
    }

}
