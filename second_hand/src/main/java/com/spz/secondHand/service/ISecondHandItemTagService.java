package com.spz.secondHand.service;

import com.spz.secondHand.entity.SecondHandItemTag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.tag.entity.dto.TagTagGroupDto;

import java.util.List;

/**
 * <p>
 * 二手物品与标签的关联表 服务类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
public interface ISecondHandItemTagService extends IService<SecondHandItemTag> {

    void savaBatchByItemIdAndTagTagGroupDtoList(int itemId, List<TagTagGroupDto> tagList);
}
