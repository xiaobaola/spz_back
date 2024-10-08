package com.spz.personal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spz.personal.entity.UserItemCollect;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_collect(用户与物品收藏关联表)】的数据库操作Mapper
* @createDate 2024-09-25 17:15:32
* @Entity com.spz.personal.entity.UserItemCollect
*/
public interface UserItemCollectMapper extends BaseMapper<UserItemCollect> {

    @Select("select item_id from user_item_collect where user_id = #{userId}")
    List<Integer> getItemIdsByUserId(int userId);
}




