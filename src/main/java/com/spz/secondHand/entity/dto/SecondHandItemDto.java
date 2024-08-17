package com.spz.secondHand.entity.dto;

import com.spz.secondHand.entity.SecondHandItem;
import lombok.Data;

@Data
public class SecondHandItemDto extends SecondHandItem {
    private String sellerUsername;
    private String sellerImage;
}
