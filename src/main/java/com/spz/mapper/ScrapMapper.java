package com.spz.mapper;

import com.spz.entity.scrap.Scrap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface ScrapMapper {
    @Select("select * from scrap where scrap_type_id = #{id}")
    ArrayList<Scrap> listByTypeId(Integer id);
}
