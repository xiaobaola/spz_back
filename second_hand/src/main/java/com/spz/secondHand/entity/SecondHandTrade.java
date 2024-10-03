package com.spz.secondHand.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author last
 * Date 2024-08-11
 * -- 二手交易表
 *     create table second_hand_trade
 *             (
 *                    id int primary key not null auto_increment comment '订单id',
 *     number      varchar(50) not null comment '订单编号',
 *     item_image varchar(100) comment '物品图片',
 *     item_price int not null comment '物品价格',
 *     item_information varchar(100) not null comment '物品信息',
 *     place varchar(50) not null comment '交易地点',
 *     approach varchar(20) not null comment '交易方式',
 *     trade_time datetime comment '交易时间',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间'
 *             ) comment '二手交易表';
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandTrade {
    private int id;
    private String number;
    private String itemImage;
    private int itemPrice;
    private String itemInformation;
    private String place;
    private String approach;
    private LocalDateTime tradeTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    SecondHandTrade(String number,String itemImage,int itemPrice,String itemInformation,String place,String approach,
//                    LocalDateTime tradeTime,LocalDateTime createTime,LocalDateTime updateTime){
//        this.number = number;
//        this.itemImage = itemImage;
//        this.itemPrice = itemPrice;
//        this.itemInformation = itemInformation;
//        this.place = place;
//        this.approach = approach;
//        this.tradeTime = tradeTime;
//        this.createTime = createTime;
//        this.updateTime = updateTime;
//    }
}
