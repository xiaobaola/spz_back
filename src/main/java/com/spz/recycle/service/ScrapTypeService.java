package com.spz.recycle.service;

import com.spz.recycle.entity.ScrapType;

import java.util.ArrayList;

public interface ScrapTypeService {
    void add(ScrapType scrapType);

    ArrayList<ScrapType> getList();

    void changeById(ScrapType scrapType);

    void deleteById(Integer id);

    ScrapType getById(int scrapTypeId);
}
