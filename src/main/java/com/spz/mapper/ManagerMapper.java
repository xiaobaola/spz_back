package com.spz.mapper;

import com.spz.entity.manager.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerMapper {
    @Select("select * from manager where username=#{username} and password=#{password}")
    Manager getByUN(Manager manager);
}
