package com.spz.manager.mapper;

import com.spz.manager.entity.Manager;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ManagerMapper {
    @Select("select * from manager where username=#{username} and password=#{password}")
    Manager getByUN(Manager manager);

    List<Manager> list(String name, LocalDate begin, LocalDate end);

    @Select("select * from manager where id=#{id}")
    Manager getById(Integer id);

//    @Insert("insert into manager(id, name, username, password, phone, update_time, create_time) ")
    @Update("update manager set name=#{name}, username=#{username}, phone=#{phone}, update_time=#{updateTime} where id=#{id}")
    void updateById(Manager manager);

    @Insert("insert into manager(name, username, phone, update_time, create_time) " +
            "VALUES (#{name}, #{username}, #{phone}, #{updateTime}, #{createTime})")
    void insert(Manager manager);
}
