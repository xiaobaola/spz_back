package com.spz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spz.entity.scrap.ScrapType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ScrapTypeMapper extends BaseMapper<ScrapType> {
    @Insert("insert into scrap_type(id, name, create_time, update_time)  values (#{id},#{name}, #{createTime}, #{updateTime})")
    public void insert2(ScrapType s);
}
