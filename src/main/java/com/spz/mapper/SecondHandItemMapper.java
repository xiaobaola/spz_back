package com.spz.mapper;

import com.spz.entity.secondhand.SecondHandItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecondHandItemMapper {
    @Select("select * from second_hand_item")
    List<SecondHandItem> selectAll();
}
