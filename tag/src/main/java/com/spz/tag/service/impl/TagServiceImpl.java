package com.spz.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.tag.entity.Tag;
import com.spz.tag.entity.TagGroup;
import com.spz.tag.entity.TagTagGroup;
import com.spz.tag.entity.dto.TagGroupDto;
import com.spz.tag.mapper.TagMapper;
import com.spz.tag.mapper.TagTagGroupMapper;
import com.spz.tag.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    private final TagTagGroupMapper tagTagGroupMapper;
    @Override
    public List<TagGroupDto> getTagDroupDtoListByTagIdList(List<Integer> tagIdList) {
        if(tagIdList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> resultSet = tagTagGroupMapper.findTagGroupsWithTags(tagIdList);

        // 聚合逻辑
        Map<Integer, TagGroupDto> tagGroupMap = new HashMap<>();
        for (Map<String, Object> row : resultSet) {
            Integer tagGroupId = (Integer) row.get("tagGroupId");
            String tagGroupName = (String) row.get("tagGroupName");
            Integer tagId = (Integer) row.get("tagId");
            String tagName = (String) row.get("tagName");

            // 如果分组未创建，创建分组对象
            TagGroupDto tagGroup = tagGroupMap.computeIfAbsent(tagGroupId, id -> {
                TagGroupDto group = new TagGroupDto();
                group.setTagList(new ArrayList<>());
                group.setId(tagGroupId);
                group.setName(tagGroupName);
                return group;
            });

            // 添加标签到分组
            Tag tag = new Tag();
            tag.setId(tagId);
            tag.setName(tagName);
            tagGroup.getTagList().add(tag);
        }

        // 返回结果列表
        return new ArrayList<>(tagGroupMap.values());
    }
}
