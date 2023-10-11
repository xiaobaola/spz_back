package com.spz.entity.secondhand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:07
 * Description:二手物品信息
 * -- 二手表
 * create table secondHand
 * (
 *     id       int auto_increment primary key comment '二手物品id',
 *     user_id          int          not null comment '发布人id',
 *     info        varchar(200) not null comment '二手物品描述',
 *     image       varchar(300) comment '二手物品图片',
 *     price       int unsigned not null comment '二手物品价格',
 *     `status`    varchar(8)   not null comment '物品的状态',
 *     create_time datetime comment '发布时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (user_id) references userMessage (id) -- 联系用户表id
 * );
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondHand {
    private Integer id;//二手物品id
    private Integer userId;//发布人id
    private String info;//二手物品描述
    private String image;//二手物品图片
    private String price;//二手物品价格
    private String status;//物品的状态
    private LocalDateTime createTime;//发布时间
    private LocalDateTime updateTime;//更新时间
}
