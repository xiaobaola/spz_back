package com.spz.secondHand.entity.dto;

import com.spz.secondHand.entity.SecondHandItem;
import com.spz.secondHand.entity.SecondHandItemImage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecondHandItemDto extends SecondHandItem {
    private String sellerUsername;
    private String sellerImage;
    private List<SecondHandItemImage> imageList;
//    标签列表 图片列表 浏览量 评论量 收藏量
}
