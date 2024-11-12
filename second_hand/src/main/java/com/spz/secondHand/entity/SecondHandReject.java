package com.spz.secondHand.entity;

//-- 二手物品审核不通过原因表
//create table second_hand_item_reject
//(
//    id int primary key auto_increment comment '审核表编号',
//    item_id int comment '二手物品id',
//    manager_id int comment '管理员id',
//    message varchar(200) not null comment '二手物品描述',
//    create_time datetime comment '创建时间',
//    update_time datetime comment '更新时间',
//    foreign key (item_id) references second_hand_item (id)-- 联系用户表的id
//) comment '二手物品审核不通过原因表';
public class SecondHandReject {
    private int id;
    private int itemId;
    private int managerId;
    private String message;
    private String createTime;
    private String updateTime;

}
