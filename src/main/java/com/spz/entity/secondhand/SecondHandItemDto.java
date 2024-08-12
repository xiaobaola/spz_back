package com.spz.entity.secondhand;

import lombok.Data;

@Data
public class SecondHandItemDto extends SecondHandItem{
    private String sellerUsername;
    private String sellerImage;
}
