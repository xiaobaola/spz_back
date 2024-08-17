package com.spz.manager.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.manager.entity.Manager;
import com.spz.public_resource.entity.page.PageBean;
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
    @Autowired
    private ManagerMapper managerMapper;

    //通过用户名和密码查询管理员信息
    @Override
    public Manager getByUN(Manager manager) {
        return managerMapper.getByUN(manager);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<Manager> managerList = managerMapper.list(name, begin, end);

        //为了获取total
        Page<Manager> managerPage = (Page<Manager>) managerList;

        //3、封装pageBean对象
        PageBean pageBean = new PageBean(managerPage.getTotal(), managerPage.getResult());

        return pageBean;
    }

    @Override
    public Manager getById(Integer id) {
        return managerMapper.getById(id);
    }

    @Override
    public void updateById(Manager manager) {
        manager.setUpdateTime(LocalDateTime.now());
        managerMapper.updateById(manager);
    }

    @Override
    public void insert(Manager manager) {
        manager.setUpdateTime(LocalDateTime.now());
        manager.setCreateTime(LocalDateTime.now());
        managerMapper.insert(manager);
    }
}
