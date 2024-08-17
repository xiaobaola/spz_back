package com.spz.service;

import com.spz.entity.manager.Manager;
import com.spz.entity.page.PageBean;

import java.time.LocalDate;

public interface ManagerService {

    Manager getByUN(Manager manager);

    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    Manager getById(Integer id);

    void updateById(Manager manager);

    void insert(Manager manager);
}
