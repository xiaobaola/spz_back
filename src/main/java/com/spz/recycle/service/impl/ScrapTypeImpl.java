package com.spz.recycle.service.impl;

import com.spz.recycle.mapper.ScrapTypeMapper;
import com.spz.recycle.entity.ScrapType;
import com.spz.recycle.service.ScrapTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ScrapTypeImpl implements ScrapTypeService {
    @Autowired
    private ScrapTypeMapper scrapTypeMapper;
    public void insert2(ScrapType scrapType) {
        scrapType.setCreateTime(LocalDateTime.now());
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.insert2(scrapType);
    }

    @Override
    public ArrayList<ScrapType> list2() {
        return scrapTypeMapper.list2();
    }

    @Override
    public void updateById(ScrapType scrapType) {
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.updateById(scrapType);
    }

    @Override
    public void deleteById(Integer id) {
        scrapTypeMapper.deleteById(id);
    }
}
