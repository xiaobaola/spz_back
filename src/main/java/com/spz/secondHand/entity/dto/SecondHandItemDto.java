package com.spz.secondHand.entity.dto;

import com.spz.secondHand.entity.SecondHandItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecondHandItemDto extends SecondHandItem {
    private String sellerUsername;
    private String sellerImage;
}
