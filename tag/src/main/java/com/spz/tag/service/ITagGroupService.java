package com.spz.tag.service;

import com.spz.tag.entity.TagGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.tag.entity.dto.TagGroupDto;

import java.util.List;

/**
 * <p>
 * 标签分组表 服务类
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
public interface ITagGroupService extends IService<TagGroup> {

    List<TagGroupDto> listTagGroupDto();
}
