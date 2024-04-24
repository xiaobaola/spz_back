package com.spz.mapper;

import com.spz.entity.message.MessageUser;
import com.spz.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageUserMapper {
    @Select("select * from message_user where sender_id=#{userId1} and receiver_id=#{userId2}")
    List<MessageUser> getBySendIdAndReceiverId(Integer userId1, Integer userId2);
}
