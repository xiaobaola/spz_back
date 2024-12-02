package com.spz.tag.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.tag.entity.Tag;
import com.spz.tag.entity.TagGroup;
import com.spz.tag.entity.TagTagGroup;
import com.spz.tag.entity.dto.TagGroupDto;
import com.spz.tag.mapper.TagGroupMapper;
import com.spz.tag.mapper.TagMapper;
import com.spz.tag.mapper.TagTagGroupMapper;
import com.spz.tag.service.ITagGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 标签分组表 服务实现类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
@Service
@RequiredArgsConstructor
public class TagGroupServiceImpl extends ServiceImpl<TagGroupMapper, TagGroup> implements ITagGroupService {
    private final TagTagGroupMapper tagTagGroupMapper;
    private final TagMapper tagMapper;
    @Override
    public List<TagGroupDto> listTagGroupDto() {
        // 1.获取自己所有的标签分组信息
        List<TagGroup> tagGroups = this.list();
        // 2.遍历标签分组信息，对标签分组信息增强，补全标签信息
        List<TagGroupDto> tagGroupDtoList = tagGroups.stream().map(tagGroup -> {
            // 2.0 创建tagGroupDto 对象拷贝
            TagGroupDto tagGroupDto = new TagGroupDto();
            BeanUtil.copyProperties(tagGroup,tagGroupDto);
            // 2.1 根据标签组id获取标签关联表中的标签组与标签的关联信息
            LambdaQueryWrapper<TagTagGroup> tagTagGroupQueryWrapper = new LambdaQueryWrapper<>();
            tagTagGroupQueryWrapper.eq(TagTagGroup::getTagGroupId,tagGroup.getId());
            List<TagTagGroup> tags = tagTagGroupMapper.selectList(tagTagGroupQueryWrapper);
            // 2.1.1 如果关联表为空，则标签列表为空 用糊涂工具类进行空判断
            if(tags.isEmpty()) {
                tagGroupDto.setTagList(null);
//                tagGroupDto.setTagList(new ArrayList<>());
                return tagGroupDto;
            }
            // 2.2 遍历关联组，根据关联组中的标签id获取标签信息
            List<Integer> tagIds = tags.stream().map(TagTagGroup::getTagId).toList();
            // 2.2.1 将标签信息放到到标签列表中
            LambdaQueryWrapper<Tag> tagQueryWrapper = new LambdaQueryWrapper<>();
            tagQueryWrapper.in(Tag::getId,tagIds);
            List<Tag> tagsInfo = tagMapper.selectList(tagQueryWrapper);
            // 2.2.2 将标签列表补全到标签分组信息中
            tagGroupDto.setTagList(tagsInfo);
            return tagGroupDto;
        }).toList();
        return tagGroupDtoList;
    }
}
