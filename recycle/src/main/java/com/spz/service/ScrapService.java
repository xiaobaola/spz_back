package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.Scrap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ScrapService {
    ArrayList<Scrap> getListByTypeId(Integer id);

    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);

    Scrap getById(Integer id);

    void changeById(Scrap scrap);

    void deleteByIds(List<Integer> ids);

    void add(Scrap scrap);
}
