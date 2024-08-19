package com.spz.recycle.mapper;

import com.spz.recycle.entity.Scrap;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ScrapMapper {

    @Select("select * from scrap where scrap_type_id = #{id}")
    ArrayList<Scrap> selectListByTypeId(Integer id);

    //xml文档映射
    List<Scrap> selectList(String name, LocalDate begin, LocalDate end);

    @Select("select * from scrap where id = #{id}")
    Scrap selectById(Integer id);

//    @Insert("insert into scrap(id, name, price, image, scrap_type_id, type, count, size, other, create_time, update_time) ")
    @Update("update scrap set name=#{name}, scrap_type_id=#{scrapTypeId}, price=#{price}, other=#{other}," +
            " image=#{image}, update_time=#{updateTime} where id=#{id}")
    void updateById(Scrap scrap);

    void deleteByIds(List<Integer> ids);

    @Insert("insert into scrap(name, price, image, scrap_type_id, other, create_time, update_time) " +
            "VALUES (#{name}, #{price}, #{image}, #{scrapTypeId}, #{other}, #{createTime}, #{updateTime})")
    void insert(Scrap scrap);
}
