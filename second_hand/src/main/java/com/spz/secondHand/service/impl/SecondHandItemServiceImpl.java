package com.spz.secondHand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.dto.SecondHandItemDto;
import com.spz.personal.entity.User;
import com.spz.secondHand.mapper.SecondHandItemMapper;
import com.spz.secondHand.service.SecondHandItemService;
import com.spz.personal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecondHandItemServiceImpl extends ServiceImpl<SecondHandItemMapper, SecondHandItem> implements SecondHandItemService {

    @Value("${spz.hasRedis}")
    private boolean hasRedis;
    private final SecondHandItemMapper itemMapper;
    private final UserService userService;
    private final RedisTemplate redisTemplate;
    @Override
    public void changeStatusById(int status, int itemId) {
        // 1->3 2->3
        itemMapper.updateStatusById(status,itemId);
    }

    @Override
    public SecondHandItem getOneById(int itemId) {
        return itemMapper.selectById(itemId);
    }

    @Override
    public List<SecondHandItemDto> getItemDtoByStatus(int status) {
        // 判断是否配置了redis 并且redis缓存中存在
        // 使用注解配置，无需写存储逻辑
//        if(hasRedis && redisTemplate.opsForValue().get("secondHandItemDto")!=null){
//            // 从redis缓存获取 并用fastjson反序列化
//            log.info("service 从redis缓存获取二手物品信息");
//            String json = (String)redisTemplate.opsForValue().get("secondHandItemDto");
//            // 假设 JSON 字符串是一个数组
//            JSONArray jsonArray = JSON.parseArray(json);
//            // 将 JSONArray 转换为 List<SecondHandItemDto>
//            return jsonArray.toJavaList(SecondHandItemDto.class);
//        }

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

//        if(hasRedis){
//            // 3.将itemDtos序列化后放入redis缓存
//            String json = JSON.toJSONString(itemDtos);
//            redisTemplate.opsForValue().set("secondHandItemDto",json,60, TimeUnit.MINUTES);
//        }

        return itemDtos;
    }

    @Override
    public void addItem(SecondHandItem item) {
        // 完善数据status 时间
        int status = 1; // 1:待审核 2:发布中 3:下架
        item.setStatus(status);
//        item.setCreateTime(LocalDateTime.now());
//        item.setUpdateTime(LocalDateTime.now());
        itemMapper.insert(item);
    }

    /**
     * Author last
     * Param @param userId 用户 ID
     * Return @return {@link List }<{@link SecondHandItem }>
     * Describe 卖家获取自己的发布
     */
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

    @Override
    public List<SecondHandItemDto> getItemDtoListBySearchInfo(String info) {
        // 对于复杂的操作可以考虑使用 redis 缓存减少频繁的操作 搜索功能除外，因为搜索信息频繁改动
        // 1 模糊查询物品的描述信息
        LambdaQueryWrapper<SecondHandItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(SecondHandItem::getInformation,info);
        // 1.2 同时状态为2 2：发布中
        queryWrapper.eq(SecondHandItem::getStatus,2);
        List<SecondHandItem> list = this.list(queryWrapper);
        // 创建返回的list
        List<SecondHandItemDto> itemDtoList = new ArrayList<>();
        // 2 遍历列表，补全信息
        for(SecondHandItem item : list) {
            // 2.0 创建itemDto
            SecondHandItemDto itemDto = new SecondHandItemDto();
            // 2.0.1 对象拷贝
            BeanUtils.copyProperties(item,itemDto);
            // 2.1 通过userId获取user信息
            User user =  userService.getById(item.getUserId());
            // 2.2 补全seller的username和image
            itemDto.setSellerUsername(user.getUsername());
            // 2.3 放入list中
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

}
