package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.Scrap;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ScrapService {
    ArrayList<Scrap> listByTypeId(Integer id);

    PageBean page(Integer page, Integer pageSize, String name, LocalDate begin, LocalDate end);
}
