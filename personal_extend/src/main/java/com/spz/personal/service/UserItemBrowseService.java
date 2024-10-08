package com.spz.personal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.personal.entity.UserItemBrowse;
import com.spz.personal.entity.dto.SecondHandBrowseDto;

import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_browse(用户与物品浏览关联表)】的数据库操作Service
* @createDate 2024-09-25 17:15:32
*/
public interface UserItemBrowseService extends IService<UserItemBrowse> {


    List<SecondHandBrowseDto> listByUserId(int userId);

    void insertOrUpdateByUserIdAndItemId(Integer userId, Integer itemId);
}
