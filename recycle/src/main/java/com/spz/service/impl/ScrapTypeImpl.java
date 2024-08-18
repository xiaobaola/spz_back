package com.spz.service.impl;

import com.spz.mapper.ScrapTypeMapper;
import com.spz.entity.ScrapType;
import com.spz.service.ScrapTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ScrapTypeImpl implements ScrapTypeService {

    private ScrapTypeMapper scrapTypeMapper;

    @Autowired
    public void setScrapTypeMapper(ScrapTypeMapper scrapTypeMapper) {
        this.scrapTypeMapper = scrapTypeMapper;
    }

    public void add(ScrapType scrapType) {
        scrapType.setCreateTime(LocalDateTime.now());
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.insert(scrapType);
    }

    @Override
    public ArrayList<ScrapType> getList() {
        return scrapTypeMapper.selectList();
    }

    @Override
    public void changeById(ScrapType scrapType) {
        scrapType.setUpdateTime(LocalDateTime.now());
        scrapTypeMapper.updateById(scrapType);
    }

    @Override
    public void deleteById(Integer id) {
        scrapTypeMapper.deleteById(id);
    }

    @Override
    public ScrapType getById(int scrapTypeId) {
        return scrapTypeMapper.selectById(scrapTypeId);
    }
}
