package com.spz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.entity.scrap.ScrapType;

import java.util.ArrayList;

public interface ScrapTypeService extends IService<ScrapType> {
    public void insert2();

    public ArrayList<ScrapType> list2();
}
