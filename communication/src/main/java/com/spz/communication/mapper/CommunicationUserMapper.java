package com.spz.communication.mapper;

import com.spz.personal_extend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface CommunicationUserMapper {

    @Select("select * from user")
    ArrayList<User> getByAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User getByInfo(User user);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Insert("insert into user(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insert(User user);

    @Select("select * from user where username like CONCAT('%', #{info}, '%')")
    List<User> getByLikeUsername(String info);

    @Select("select * from user where phone like CONCAT('%', #{info}, '%')")
    List<User> getByLikePhone(String info);

    @Select("select * from user where id like CONCAT('%', #{info}, '%')")
    List<User> getByLikeId(String info);

    @Select("select * from user where phone=#{phone}")
    User getByPhone(String phone);

}
