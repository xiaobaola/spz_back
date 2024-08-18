package com.spz.mapper;

import com.spz.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRegisterMapper {
    @Insert("insert into user(username, phone, password,update_time, create_time) values (#{username},#{phone},#{password},#{updateTime},#{createTime}) ")
    void insert(User user);

    @Select("select id from user where username=#{username}")
    Integer selectIdByUserName(String username);
}
