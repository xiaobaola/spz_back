package com.spz.tag.controller;


import com.spz.common.Res;
import com.spz.tag.entity.dto.TagGroupDto;
import com.spz.tag.service.ITagGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author last
 * @since 2024-12-01
 */
@RestController
@RequestMapping("/spz/tag")
@RequiredArgsConstructor
public class TagController {
    private final ITagGroupService tagGroupService;

    // 获取所有的标签 包括标签组以及标签组下的标签
    @GetMapping("/list")
    public Res<List<TagGroupDto>>list() {
        return Res.success(tagGroupService.listTagGroupDto());
    }
}
