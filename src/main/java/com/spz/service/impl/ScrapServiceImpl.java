package com.spz.service.impl;

import com.spz.entity.scrap.Scrap;
import com.spz.mapper.ScrapMapper;
import com.spz.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScrapServiceImpl implements ScrapService {
    @Autowired
    private ScrapMapper scrapMapper;

    @Override
    public ArrayList<Scrap> listByTypeId(Integer id) {
        return scrapMapper.listByTypeId(id);
    }
}
