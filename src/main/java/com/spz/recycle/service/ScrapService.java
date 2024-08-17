package com.spz.recycle.service;

import com.spz.public_resource.entity.page.PageBean;
import com.spz.recycle.entity.Scrap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ScrapService {
    public ArrayList<Scrap> listByTypeId(Integer id);

    public PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    public Scrap getById(Integer id);

    void updateById(Scrap scrap);

    void deleteByIds(List<Integer> ids);

    void insert(Scrap scrap);
}
