package com.spz.mapper;

import com.spz.entity.safe.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SafeMapper {
    @Select("select * from token where token=#{token}")
    Token getByToken(String token);

    @Insert("insert into token(token, create_time) VALUES (#{token},#{createTime})")
    void insert(Token token);
}
