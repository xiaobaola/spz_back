package com.spz.secondHand.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.secondHand.entity.SecondHandItemTag;
import com.spz.secondHand.mapper.SecondHandItemTagMapper;
import com.spz.secondHand.service.ISecondHandItemTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.tag.entity.TagTagGroup;
import com.spz.tag.entity.dto.TagTagGroupDto;
import com.spz.tag.mapper.TagTagGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 二手物品与标签的关联表 服务实现类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecondHandItemTagServiceImpl extends ServiceImpl<SecondHandItemTagMapper, SecondHandItemTag> implements ISecondHandItemTagService {

    private final TagTagGroupMapper tagTagGroupMapper;

    @Override
    public void savaBatchByItemIdAndTagTagGroupDtoList(int itemId, List<TagTagGroupDto> tagList) {
        List<SecondHandItemTag> secondHandItemTagList = new ArrayList<>();
        // 根据标签id和标签组id查询 标签关联id
        for (TagTagGroupDto tagTagGroupDto : tagList) {
            int tagId = tagTagGroupDto.getTagId();
            int tagGroupId = tagTagGroupDto.getTagGroupId();
            log.info("tagId: {}, tagGroupId: {}", tagId, tagGroupId);
            LambdaQueryWrapper<TagTagGroup> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TagTagGroup::getTagId,tagId).eq(TagTagGroup::getTagGroupId,tagGroupId);
            TagTagGroup tagTagGroup = tagTagGroupMapper.selectOne(wrapper);
            SecondHandItemTag secondHandItemTag = new SecondHandItemTag();
            secondHandItemTag.setSecondHandItemId(itemId);
            secondHandItemTag.setTagTagGroupId(tagTagGroup.getId());

            secondHandItemTagList.add(secondHandItemTag);
        }
        this.saveBatch(secondHandItemTagList);
    }
}
