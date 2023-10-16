package com.spz.mapper;

import com.spz.entity.scrap.Scrap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ScrapMapper {

    @Select("select * from scrap where scrap_type_id = #{id}")
    ArrayList<Scrap> listByTypeId(Integer id);

    //xml文档映射
    List<Scrap> list(String name, LocalDate begin, LocalDate end);
}
