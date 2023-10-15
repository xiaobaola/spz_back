package com.spz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.mapper.ScrapTypeMapper;
import com.spz.entity.scrap.ScrapType;
import com.spz.service.ScrapTypeService;
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
