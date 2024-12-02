package com.spz.tag.service;

import com.spz.tag.entity.TagTagGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.tag.entity.dto.TagTagGroupDto;

import java.util.List;

/**
 * <p>
 * 标签与分组的关联表 服务类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
public interface ITagTagGroupService extends IService<TagTagGroup> {

    TagTagGroup getTagTagGroupByTagIdAndTagGroupId(int tagId, int tagGroupId);

    List<TagTagGroupDto> getTagTagGroupDtoListByTagTagGroupIdList(List<Integer> tagIdList);
}
