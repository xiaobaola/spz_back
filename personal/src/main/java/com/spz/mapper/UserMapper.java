package com.spz.mapper;

import com.spz.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user")
    ArrayList<User> selectByAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User selectByUsernameAndPassword(String username,String password);

    void updateById(User user);

    List<User> selectByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Insert("insert into user(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insert(User user);

    @Select("select * from user where username like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikeUsername(String info);

    @Select("select * from user where phone like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikePhone(String info);

    @Select("select * from user where id like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikeId(String info);

    @Select("select * from user where phone=#{phone}")
    User selectByPhone(String phone);

    List<User> selectUsersByUserIds(List<Integer> userIds);
}
