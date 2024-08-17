package com.spz.recycle.service;

import com.spz.recycle.entity.ScrapType;

import java.util.ArrayList;

public interface ScrapTypeService {
    public void insert2(ScrapType scrapType);

    public ArrayList<ScrapType> list2();

    void updateById(ScrapType scrapType);

    void deleteById(Integer id);

}
