package com.spz.personal_extend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.personal_extend.entity.UserItemCollect;
import com.spz.personal_extend.mapper.UserItemCollectMapper;
import com.spz.personal_extend.service.UserItemCollectService;
import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.service.SecondHandItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_collect(用户与物品收藏关联表)】的数据库操作Service实现
* @createDate 2024-09-25 17:15:32
*/
@Service
public class UserItemCollectServiceImpl extends ServiceImpl<UserItemCollectMapper, UserItemCollect>
    implements UserItemCollectService {

    @Autowired
    private UserItemCollectMapper userItemCollectMapper;

    @Autowired
    private SecondHandItemService secondHandItemService;
    @Override
    public List<SecondHandItem> listByUserId(int userId) {
        // 1 查找userId浏览记录中的itemIds
        List<Integer> itemIds = userItemCollectMapper.getItemIdsByUserId(userId);

        if (itemIds.isEmpty()) {
            return null;
        }


        // 2 根据itemIds 查找 所有二手物品
        LambdaQueryWrapper<SecondHandItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SecondHandItem::getId, itemIds);

        // 3 排序 按collect的updateTime降序 比较困难
        return secondHandItemService.list(queryWrapper);
    }

    @Override
    public Integer hasOneByUserIdAndItemId(int userId, int itemId) {
        LambdaQueryWrapper<UserItemCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserItemCollect::getUserId, userId);
        queryWrapper.eq(UserItemCollect::getItemId, itemId);
        UserItemCollect item = getOne(queryWrapper);
        if (item != null) {
            return 1;
        }
        return 0;
    }
}




