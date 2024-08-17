package com.spz.communication.entity.message;


/*
*
* create table message_trade(
    id int primary key auto_increment comment '用户ID',
    name varchar(100) default 'momo' comment '用户名',
    message varchar(500) not null comment '信息',
    trade_time datetime comment '交易时间',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
);
*
* */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageTrade {
    private Integer id;
    private String name;
    private String message;
    private LocalDateTime tradeTimeStart;
    private LocalDateTime tradeTimeFinish;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
