package com.spz.secondHand.mapper;

import com.spz.secondHand.entity.SecondHandItem;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into second_hand_item (image, status, price, information, create_time, update_time, user_id) " +
            "values (#{image},#{status},#{price},#{information},#{createTime},#{updateTime},#{userId})")
    void insert(SecondHandItem item);

    @Select("select * from second_hand_item where user_id=#{userId}")
    List<SecondHandItem> selectByUserId(int userId);

//    @Update("update second_hand_item set image=#{image}, price=#{price},information=#{information},status=#{status} where id=#{id}")
    void updateByItem(SecondHandItem item);

    @Delete("delete from second_hand_item where id=#{id}")
    void deleteById(int id);
}
