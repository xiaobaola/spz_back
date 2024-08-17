package com.spz.personal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ChangeMapper {

    @Update("update user set username=#{username} where id=#{id}")
    void changeUserName(Integer id ,String username);

    @Update("update user set phone=#{phone} where id=#{id}")
    void changePhone(Integer id ,String phone);

    @Update("update user set password=#{password} where id=#{id}")
    void changePassword(Integer id ,String password);
}
