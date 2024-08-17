package com.spz.mapper;

import com.spz.entity.message.MessageUser;
import com.spz.entity.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageUserMapper {
    @Select("select * from message_user where sender_id=#{userId1} and receiver_id=#{userId2}")
    List<MessageUser> getBySendIdAndReceiverId(Integer userId1, Integer userId2);

    @Insert("insert into message_user(sender_id, receiver_id, message, mes_status, update_time, create_time) " +
            "values(#{senderId},#{receiverId},#{message},#{mesStatus},#{updateTime},#{createTime})")
    void insert(MessageUser messageUser);
}