package com.spz.manager.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.manager.entity.Manager;
import com.spz.public_resouce.entity.page.PageBean;
import com.spz.manager.mapper.ManagerMapper;
import com.spz.manager.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ManagerServiceImpl implements ManagerService {

    private ManagerMapper managerMapper;

    @Autowired
    public void setManagerMapper(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    //通过用户名和密码查询管理员信息
    @Override
    public Manager getByUsernameAndPassword(String username, String password) {
        return managerMapper.selectByUsernameAndPassword(username,password);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<Manager> managerList = managerMapper.selectList(name, begin, end);

        //为了获取total
        Page<Manager> managerPage = (Page<Manager>) managerList;

        //3、封装pageBean对象

        return new PageBean(managerPage.getTotal(), managerPage.getResult());
    }

    @Override
    public Manager getById(Integer id) {
        return managerMapper.selectById(id);
    }

    @Override
    public void updateById(Manager manager) {
        manager.setUpdateTime(LocalDateTime.now());
        managerMapper.updateById(manager);
    }

    @Override
    public void add(Manager manager) {
        manager.setUpdateTime(LocalDateTime.now());
        manager.setCreateTime(LocalDateTime.now());
        managerMapper.insert(manager);
    }
}
