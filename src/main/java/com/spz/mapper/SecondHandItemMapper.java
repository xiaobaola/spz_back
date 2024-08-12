package com.spz.mapper;

import com.spz.entity.secondhand.SecondHandItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SecondHandItemMapper {
    @Select("select * from second_hand_item where status = #{status}")
    List<SecondHandItem> selectByStatus(int status);

    @Update("update second_hand_item set  status = #{status} where id = #{itemId}")
    void updateStatusById(int status, int itemId);

    @Select("select user_id from second_hand_item where id=#{itemId}")
    int selectUserIdById(int itemId);

    @Select("select * from second_hand_item where id=#{itemId}")
    SecondHandItem selectById(int itemId);
}
