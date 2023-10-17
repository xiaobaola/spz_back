package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.manager.Manager;
import com.spz.entity.page.PageBean;
import com.spz.entity.user.UserMessage;
import com.spz.mapper.UserMessageMapper;
import com.spz.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    UserMessageMapper userMessageMapper;

    @Override
    public UserMessage getById(UserMessage userMessage) {
        return userMessageMapper.getById(userMessage);
    }

    @Override
    public UserMessage getByInfo(UserMessage userMessage) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMessageMapper.getByInfo(userMessage);
    }

    @Override
    public void updeteById(UserMessage userMessage) {
        userMessage.setUpdateTime(LocalDateTime.now());
        userMessageMapper.updateById(userMessage);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<UserMessage> userList = userMessageMapper.list(username, begin, end);

        //为了获取total
        Page<UserMessage> userPage = (Page<UserMessage>) userList;

        //3、封装pageBean对象
        PageBean pageBean = new PageBean(userPage.getTotal(), userPage.getResult());

        return pageBean;
    }

    @Override
    public UserMessage getByIdNumber(Integer id) {
        return userMessageMapper.getByIdNumber(id);
    }

    @Override
    public void insert(UserMessage userMessage) {
        userMessage.setUpdateTime(LocalDateTime.now());
        userMessage.setCreateTime(LocalDateTime.now());
        userMessageMapper.insert(userMessage);
    }
}
