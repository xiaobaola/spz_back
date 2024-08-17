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

    @Select("select * from user")
    ArrayList<User> selectAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User selectByUsernameAndPassword(String username,String password);

    void updateById(User user);

    List<User> selectByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Insert("insert into user(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insert(User user);

    @Select("select * from user where username like CONCAT('%', #{likeUsername}, '%')")
    List<User> selectByLikeUsername(String likeUsername);

    @Select("select * from user where phone like CONCAT('%', #{info}, '%')")
    List<User> selectByLikePhone(String info);

    @Select("select * from user where id like CONCAT('%', #{info}, '%')")
    List<User> selectByLikeId(String info);

    @Select("select * from user where phone=#{phone}")
    User selectByPhone(String phone);

    List<User> selectUsersByUserIds(List<Integer> userIds);
}
