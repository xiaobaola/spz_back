package com.spz.personal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.spz.personal.entity.UserItemBrowse;

import java.util.List;

/**
* @author 86134
* @description 针对表【user_item_browse(用户与物品浏览关联表)】的数据库操作Mapper
* @createDate 2024-09-25 17:15:32
* @Entity com.spz.personal.entity.UserItemBrowse
*/
@Mapper
public interface UserItemBrowseMapper extends BaseMapper<UserItemBrowse> {

    @Select("select item_id from user_item_browse where user_id = #{userId}")
    List<Integer> getItemIdsByUserId(int userId);
}




