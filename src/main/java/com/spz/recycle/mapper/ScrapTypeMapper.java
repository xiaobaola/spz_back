package com.spz.recycle.mapper;

import com.spz.recycle.entity.ScrapType;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
//@Component
public interface ScrapTypeMapper {
    @Insert("insert into scrap_type(name, image, create_time, update_time)  values (#{name}, #{image}, #{createTime}, #{updateTime})")
    public void insert2(ScrapType scrapType);

    @Select("select * from scrap_type")
    public ArrayList<ScrapType> list2();

    @Select("select * from scrap_type where id=#{id}")
    ScrapType getById(Integer id);

    @Update("update scrap_type set name=#{name}, image=#{image}, update_time=#{updateTime} where id=#{id}")
    void updateById(ScrapType scrapType);

    @Delete("delete from scrap_type where id=#{id}")
    void deleteById(Integer id);
}
