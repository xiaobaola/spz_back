package com.spz.manager.service;

import com.spz.manager.entity.Manager;
import com.spz.entity.page.PageBean;

import java.time.LocalDate;

public interface ManagerService {

    Manager getByUsernameAndPassword(String username, String password);

    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    Manager getById(Integer id);

    void updateById(Manager manager);

    void add(Manager manager);
}
