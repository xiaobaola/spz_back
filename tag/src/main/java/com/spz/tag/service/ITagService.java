package com.spz.tag.service;

import com.spz.tag.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.tag.entity.dto.TagGroupDto;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
public interface ITagService extends IService<Tag> {

    List<TagGroupDto> getTagDroupDtoListByTagIdList(List<Integer> tagIdList);
}
