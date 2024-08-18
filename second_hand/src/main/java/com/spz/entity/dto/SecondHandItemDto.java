package com.spz.entity.dto;

import com.spz.entity.SecondHandItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SecondHandItemDto extends SecondHandItem {
    private String sellerUsername;
    private String sellerImage;
}
