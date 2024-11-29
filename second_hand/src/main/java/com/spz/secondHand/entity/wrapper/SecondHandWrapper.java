package com.spz.secondHand.entity.wrapper;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SecondHandWrapper{
    private int userId;
    private int itemId;
    private int tradeId;
    private int buyerId;
    private int sellerId;
    private String place;
    private String approach;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime tradeTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime updateTime;
    private @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDateTime createTime;
    // 二手物品
    private String image;
    private int price;
    private String name;
    private String information;
    private int managerId;
    private String message;
    private List<String> photoList;
    private int id;
    private int status; //1:待审核 2:发布中 3:下架
}
