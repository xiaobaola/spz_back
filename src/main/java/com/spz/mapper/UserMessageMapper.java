package com.spz.mapper;

import com.spz.entity.user.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMessageMapper {

    @Select("select * from user_message where id = #{id}")
    public UserMessage getById(UserMessage userMessage);
}
