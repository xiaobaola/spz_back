package com.spz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Author last
 * Date 2024-08-12
 * -- 二手交易表与用户表的关联表
 * create table second_hand_trade_user
 * (
 *     id int primary key not null auto_increment comment 'id',
 *     second_hand_trade_id int not null comment '二手交易订单id',
 *     second_hand_trade_status int not null comment '二手交易订单状态 1:创建 2:取消 3:完成 4:删除',
 *     buyer_id int not null comment '买家id',
 *     buyer_status int not null comment '买家状态 1:创建 2:取消 3:完成 4:删除',
 *     seller_id int not null comment '卖家id',
 *     seller_status int not null comment '卖家状态 1:创建 2:取消 3:完成 4:删除',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间'
 * ) comment '二手交易表与用户表的关联表';
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandTradeUser {
    private int id;
    private int secondHandTradeId;
    private int secondHandTradeStatus;
    private int buyerId;
    private int buyerStatus;
    private int sellerId;
    private int sellerStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
//    SecondHandTradeUser(int secondHandTradeId,int secondHandTradeStatus,int buyerId,int buyerStatus,
//                        int sellerId,int sellerStatus,LocalDateTime createTime,LocalDateTime updateTime){
//        this.secondHandTradeId = secondHandTradeId;
//        this.secondHandTradeStatus = secondHandTradeStatus;
//        this.buyerId = buyerId;
//        this.buyerStatus = buyerStatus;
//        this.sellerId = sellerId;
//        this.sellerStatus = sellerStatus;
//        this.createTime = createTime;
//        this.updateTime = updateTime;
//    }
}
