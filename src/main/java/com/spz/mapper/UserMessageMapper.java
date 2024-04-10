package com.spz.mapper;

import com.spz.entity.user.UserMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    void updateById(UserMessage userMessage);

    List<UserMessage> list(String username, LocalDate begin, LocalDate end);

    @Select("select * from user_message where id = #{id}")
    UserMessage getByIdNumber(Integer id);

    @Insert("insert into user_message(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insert(UserMessage userMessage);

    @Select("select * from user_message where id = #{id}")
    UserMessage getUserById(Integer id);
}
