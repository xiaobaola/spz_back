package com.spz.service;

import com.spz.entity.scrap.ScrapType;

import java.util.ArrayList;

public interface ScrapTypeService {
    public void insert2(ScrapType scrapType);

    public ArrayList<ScrapType> list2();

    void updateById(ScrapType scrapType);

    void deleteById(Integer id);
}
