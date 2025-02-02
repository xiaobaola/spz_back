package com.spz.secondHand.service.impl;

import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.dto.SecondHandItemDto;
import com.spz.personal.entity.User;
import com.spz.secondHand.mapper.SecondHandItemMapper;
import com.spz.secondHand.service.SecondHandItemService;
import com.spz.personal.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecondHandItemServiceImpl implements SecondHandItemService {

    private SecondHandItemMapper itemMapper;

    private UserService userService;

    @Autowired
    public void setItemMapper(SecondHandItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<SecondHandItem> getSomeByStatus(int status) {
        return itemMapper.selectByStatus(status);
    }

    @Override
    public void changeStatusById(int status, int itemId) {
        // 1->3 2->3
        itemMapper.updateStatusById(status,itemId);
    }

    @Override
    public int getUserIdById(int itemId) {
        return itemMapper.selectUserIdById(itemId);
    }

    @Override
    public SecondHandItem getOneById(int itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    public List<SecondHandItemDto> getItemDtoByStatus(int status) {
        List<SecondHandItemDto> itemDtos= new ArrayList<>();
        // 1获取所有物品信息 根据status
        List<SecondHandItem> items = itemMapper.selectByStatus(status);
        // 2遍历物品，对物品增强,补全
        for(SecondHandItem item : items) {
            // 2.0 创建itemDto
            SecondHandItemDto itemDto = new SecondHandItemDto();
            // 2.0.1 对象拷贝
            BeanUtils.copyProperties(item,itemDto);
            // 2.1 通过userId获取user信息
            User user =  userService.getById(item.getUserId());
            // 2.2 补全seller的username和image
            itemDto.setSellerImage(user.getImage());
            itemDto.setSellerUsername(user.getUsername());
            // 2.3 放入list中
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

    @Override
    public void createItem(SecondHandItem item) {
        // 完善数据status 时间
        int status = 1; // 1:待审核 2:发布中 3:下架
        item.setStatus(status);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        itemMapper.insert(item);
    }

    @Override
    public List<SecondHandItem> getSomeByUserId(int userId) {
        List<SecondHandItem> items = itemMapper.selectByUserId(userId);
        // 按照updateTime排序
        // 如果items不为null且包含元素，则按updateTime排序
        if (items != null && !items.isEmpty()) {
            items.sort((item1, item2) -> item2.getUpdateTime().compareTo(item1.getUpdateTime()));
        }
        return items;
    }

    @Override
    public void changeItemByItem(SecondHandItem item) {
        // 将物品状态设置为待审核状态
        item.setStatus(1);
        item.setUpdateTime(LocalDateTime.now());
        // 动态sql
        itemMapper.updateByItem(item);
    }

    @Override
    public void deleteByItemId(int itemId) {
        itemMapper.deleteById(itemId);
    }

    @Override
    public void changeItemStatusByItemId(int status, int itemId) {
        itemMapper.updateStatusById(status,itemId);
    }
}
