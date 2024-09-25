package com.spz.personal_extend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.personal_extend.entity.dto.SecondHandBrowseDto;
import com.spz.personal_extend.mapper.UserItemBrowseMapper;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.service.SecondHandItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spz.personal_extend.entity.UserItemBrowse;
import com.spz.personal_extend.service.UserItemBrowseService;

import java.util.ArrayList;
import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_browse(用户与物品浏览关联表)】的数据库操作Service实现
* @createDate 2024-09-25 17:15:32
*/
@Service
public class UserItemBrowseServiceImpl extends ServiceImpl<UserItemBrowseMapper, UserItemBrowse>
    implements UserItemBrowseService {

    @Autowired
    private UserItemBrowseMapper userItemBrowseMapper;

    @Autowired
    private SecondHandItemService secondHandItemService;
    @Override
    public List<SecondHandBrowseDto> listByUserId(int userId) {
        // 1 查找userId浏览记录中的itemIds
        LambdaQueryWrapper<UserItemBrowse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserItemBrowse::getUserId, userId);
        List<UserItemBrowse> itemIds = list(queryWrapper);

        // 2 根据 关联类 查找 所有二手物品
        List<SecondHandBrowseDto> itemDtoList = new ArrayList<>();
        for (UserItemBrowse itemId : itemIds) {
            SecondHandBrowseDto itemDto = new SecondHandBrowseDto();
            // 对象拷贝
            SecondHandItem item = secondHandItemService.getOneById(itemId.getItemId());
            BeanUtils.copyProperties(item,itemDto);
            itemDto.setCount(itemId.getCount());
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }

    @Override
    public void insertOrUpdateByUserIdAndItemId(Integer userId, Integer itemId) {
        // 1 先查询
        LambdaQueryWrapper<UserItemBrowse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserItemBrowse::getUserId, userId);
        queryWrapper.eq(UserItemBrowse::getItemId, itemId);
        UserItemBrowse item = getOne(queryWrapper);
        if (item != null) {
            // 2.1 有记录 count+1 更新操作
            item.setCount(item.getCount() + 1);
            updateById(item);
        } else {
            // 2.2 无记录 新增操作
            UserItemBrowse userItemBrowse = new UserItemBrowse();
            userItemBrowse.setUserId(userId);
            userItemBrowse.setItemId(itemId);
            userItemBrowse.setCount(1);
            save(userItemBrowse);
        }
    }
}




