package com.spz.service.impl;

import com.spz.entity.secondhand.SecondHandItem;
import com.spz.mapper.SecondHandItemMapper;
import com.spz.service.SecondHandItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecondHandItemServiceImpl implements SecondHandItemService {
    @Autowired
    private SecondHandItemMapper secondHandItemMapper;
    @Override
    public List<SecondHandItem> selectAll() {
        return secondHandItemMapper.selectAll();
    }
}
