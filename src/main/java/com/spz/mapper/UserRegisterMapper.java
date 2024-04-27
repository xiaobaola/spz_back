package com.spz.mapper;

import com.spz.entity.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRegisterMapper {
    @Insert("insert into user(username, phone, password,update_time, create_time) values (#{username},#{phone},#{password},#{updateTime},#{createTime}) ")
    void userRegister(User user);
}
