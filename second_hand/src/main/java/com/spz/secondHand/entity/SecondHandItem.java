package com.spz.secondHand.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  -- 二手物品表
 * create table second_hand_item
 * (
 *     id int primary key auto_increment comment '物品编号',
 *     image varchar(100) not null comment '二手物品图片',
 *     status int comment '二手物品状态 1:待审核 2:发布中 3:下架',
 *     price int comment '二手物品价格',
 *     information varchar(200) not null comment '二手物品描述',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     user_id int not null comment '用户id',
 *     foreign key (user_id) references user (id)-- 联系用户表的id
 * ) comment '二手物品表';
 * Author last
 * Date 2024-08-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondHandItem {
    private int id;
    private String image;
    private int status; //1:待审核 2:发布中 3:下架
    private int price;
    private String information;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private int userId;
}
