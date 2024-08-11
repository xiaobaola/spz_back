package com.spz.entity.secondhand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author last
 * Date 2024-08-11
 *
 * -- 二手交易表
 *     create table second_hand_trade
 *             (
 *                     id int primary key not null auto_increment comment '订单id',
 *                     number      varchar(50) not null comment '订单编号',
 *     item_id int not null comment '物品编号',
 *     place varchar(10) not null comment '交易地点',
 *     approach varchar(10) not null comment '交易方式',
 *     status int comment '交易状态',
 *     trade_time datetime comment '交易时间',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     buyer_id int not null comment '买家id',
 *     seller_id int not null comment '卖家id',
 *     foreign key (buyer_id) references user (id),
 *     foreign key (seller_id) references user (id)
 *             ) comment '二手交易表';
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandTrade {
    private int id;
    private String number;
    private int itemId;
    private String place;
    private String approach;
    private int status;
    private LocalDateTime tradeTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int buyerId;
    private int sellerId;
}
