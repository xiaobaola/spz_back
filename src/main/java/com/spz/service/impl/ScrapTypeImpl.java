package com.spz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.mapper.ScrapTypeMapper;
import com.spz.entity.scrap.ScrapType;
import com.spz.service.ScrapTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScrapTypeImpl extends ServiceImpl<ScrapTypeMapper, ScrapType> implements ScrapTypeService {
    @Autowired
    private ScrapTypeMapper scrapTypeMapper;
    public void insert2() {
        ScrapType s = new ScrapType(12,"塑料", LocalDateTime.now(), LocalDateTime.now());
        scrapTypeMapper.insert2(s);
    }
}
