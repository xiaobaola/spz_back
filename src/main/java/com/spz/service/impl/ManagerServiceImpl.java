package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.dto.ScrapDto;
import com.spz.entity.manager.Manager;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.Scrap;
import com.spz.entity.scrap.ScrapType;
import com.spz.mapper.ManagerMapper;
import com.spz.service.ManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
