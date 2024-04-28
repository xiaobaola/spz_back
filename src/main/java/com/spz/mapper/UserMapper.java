package com.spz.mapper;

import com.spz.entity.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    public User getById(User user);

//    @Select("select * from user where username = #{username} and password = #{password}")
//    public UserMessage getByInfo(UserMessage userMessage);

    @Select("select * from user")
    ArrayList<User> getByAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User getByInfo(User user);

    void updateById(User user);

    List<User> list(String username, LocalDate begin, LocalDate end);

    @Select("select * from user where id = #{id}")
    User getByIdNumber(Integer id);

    @Insert("insert into user(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);
}