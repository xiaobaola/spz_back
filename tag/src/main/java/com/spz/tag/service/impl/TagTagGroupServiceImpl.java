package com.spz.tag.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.tag.entity.TagTagGroup;
import com.spz.tag.entity.dto.TagTagGroupDto;
import com.spz.tag.mapper.TagGroupMapper;
import com.spz.tag.mapper.TagMapper;
import com.spz.tag.mapper.TagTagGroupMapper;
import com.spz.tag.service.ITagTagGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签与分组的关联表 服务实现类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
@Service
@RequiredArgsConstructor
public class TagTagGroupServiceImpl extends ServiceImpl<TagTagGroupMapper, TagTagGroup> implements ITagTagGroupService {
    private final TagMapper tagMapper;
//    private final TagTagGroupMapper tagTagGroupMapper;
    private final TagGroupMapper tagGroupMapper;

    @Override
    public TagTagGroup getTagTagGroupByTagIdAndTagGroupId(int tagId, int tagGroupId) {
        LambdaQueryWrapper<TagTagGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TagTagGroup::getTagId, tagId);
        queryWrapper.eq(TagTagGroup::getTagGroupId, tagGroupId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<TagTagGroupDto> getTagTagGroupDtoListByTagTagGroupIdList(List<Integer> tagIdList) {
        // 根据idList 查询出所有的tagTagGroup
        if (tagIdList.isEmpty()) return new ArrayList<>();
        List<TagTagGroup> tagTagGroupList = this.listByIds(tagIdList);
        if (tagTagGroupList.isEmpty()) return new ArrayList<>();
        List<TagTagGroupDto> tagTagGroupDtoList = new ArrayList<>();
        for (TagTagGroup tagTagGroup : tagTagGroupList) {
            Integer tagId = tagTagGroup.getTagId();
            Integer tagGroupId = tagTagGroup.getTagGroupId();
            String tagName = tagMapper.selectById(tagId).getName();
            String tagGroupName = tagGroupMapper.selectById(tagGroupId).getName();
            // 对象拷贝
            TagTagGroupDto tagTagGroupDto = new TagTagGroupDto();
            BeanUtil.copyProperties(tagTagGroup, tagTagGroupDto);
            tagTagGroupDto.setTagName(tagName);
            tagTagGroupDto.setTagGroupName(tagGroupName);

            tagTagGroupDtoList.add(tagTagGroupDto);
        }
        return tagTagGroupDtoList;
    }
}
