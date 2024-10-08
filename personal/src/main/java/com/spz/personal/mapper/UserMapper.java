package com.spz.personal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spz.personal.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    ArrayList<User> selectByAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User selectByUsernameAndPassword(String username,String password);

    void updateUserById(User user);

    List<User> selectByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    @Insert("insert into user(username, phone, gender, create_time, update_time) " +
            "VALUES(#{username}, #{phone}, #{gender}, #{createTime}, #{updateTime}) ")
    void insertWithUsernameAndPhoneAndGender(User user);

    @Select("select * from user where username like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikeUsername(String info);

    @Select("select * from user where phone like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikePhone(String info);

    @Select("select * from user where id like CONCAT('%', #{info}, '%')")
    List<User> selectListByLikeId(String info);

    @Select("select * from user where phone=#{phone}")
    User selectByPhone(String phone);

    List<User> selectUsersByUserIds(List<Integer> userIds);

    @Select("select * from user where open_id=#{openid}")
    User selectByOpenId(String openid);
}
