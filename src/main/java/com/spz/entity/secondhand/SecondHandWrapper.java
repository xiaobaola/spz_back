package com.spz.entity.secondhand;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class SecondHandWrapper {
    private int userId;
    private int itemId;
    private int tradeId;
    private int buyerId;
    private int sellerId;
    private String place;
    private String approach;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime tradeTime;
    // 二手物品
    private String image;
    private int price;
    private String information;
}
