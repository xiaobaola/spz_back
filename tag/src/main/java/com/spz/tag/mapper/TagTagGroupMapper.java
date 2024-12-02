package com.spz.tag.mapper;

import com.spz.tag.entity.TagTagGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签与分组的关联表 Mapper 接口
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
public interface TagTagGroupMapper extends BaseMapper<TagTagGroup> {

    List<Map<String, Object>> findTagGroupsWithTags(List<Integer> tagIdList);
}
