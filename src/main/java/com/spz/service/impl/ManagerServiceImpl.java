package com.spz.service.impl;

import com.spz.entity.manage.Manager;
import com.spz.mapper.ManagerMapper;
import com.spz.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    //通过用户名和密码查询管理员信息
    @Override
    public Manager getByUN(Manager manager) {
        return managerMapper.getByUN(manager);
    }
}
