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
    public List<SecondHandItem> getSomeByStatus(int status) {
        return secondHandItemMapper.selectByStatus(status);
    }

    @Override
    public void changeStatusById(int status, int itemId) {
        // 1->3 2->3
        secondHandItemMapper.updateStatusById(status,itemId);
    }

    @Override
    public int getUserIdById(int itemId) {
        return secondHandItemMapper.selectUserIdById(itemId);
    }

    @Override
    public SecondHandItem getOneById(int itemId) {
        return secondHandItemMapper.selectById(itemId);
    }
}
