package com.spz.personal_extend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.personal_extend.entity.UserItemCollect;
import com.spz.secondHand.entity.SecondHandItem;

import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_collect(用户与物品收藏关联表)】的数据库操作Service
* @createDate 2024-09-25 17:15:32
*/
public interface UserItemCollectService extends IService<UserItemCollect> {

    List<SecondHandItem> listByUserId(int userId);
}
