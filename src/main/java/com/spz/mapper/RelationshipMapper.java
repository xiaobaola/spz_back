package com.spz.mapper;

import com.spz.entity.user.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RelationshipMapper {

    @Select("select userId2 from userId1=#{userId1} and status=#{status}")
    List<Integer> getUserId2ByUserId1(Integer userId1,Integer status);

    @Select("select userId1 from userId1=#{userId2} and status=#{status}")
    List<Integer> getUserId1ByUserId2(Integer userId2,Integer status);

    @Select("select * from user_message where id=#{userId}")
    UserMessage getUserByUserId(Integer userId);
}
