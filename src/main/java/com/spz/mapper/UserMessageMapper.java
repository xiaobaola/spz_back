package com.spz.mapper;

import com.spz.entity.user.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface UserMessageMapper {

    @Select("select * from user_message where id = #{id}")
    public UserMessage getById(UserMessage userMessage);

//    @Select("select * from user_message where username = #{username} and password = #{password}")
//    public UserMessage getByInfo(UserMessage userMessage);

    @Select("select * from user_message")
    ArrayList<UserMessage> getByAll();

    @Select("select * from user_message where username=#{username} and password=#{password}")
    UserMessage getByInfo(UserMessage userMessage);
}
