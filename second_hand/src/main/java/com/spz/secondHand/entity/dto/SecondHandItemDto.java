package com.spz.secondHand.entity.dto;

import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.SecondHandItemImage;
import com.spz.tag.entity.dto.TagGroupDto;
import com.spz.tag.entity.dto.TagTagGroupDto;
import com.spz.tag.entity.wrapper.TagWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecondHandItemDto extends SecondHandItem {
    private int browseCount;
    private String sellerUsername;
    private String sellerImage;
    private List<String> imageList;
    private List<TagTagGroupDto> tagList;
//    标签列表 图片列表 浏览量 评论量 收藏量
}
