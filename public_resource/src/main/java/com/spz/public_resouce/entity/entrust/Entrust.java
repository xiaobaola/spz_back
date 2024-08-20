package com.spz.public_resouce.entity.entrust;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:04
 * Description:关于发布委托的信息类
 * -- 委托表
 * -- 委托表
 * create table entrust
 * (
 *     id       int primary key auto_increment comment '委托id',
 *     user_id          int          not null comment '用户id',
 *     info        varchar(200) not null comment '委托的需求（文字）',
 *     image       varchar(300) comment '委托的图片',
 *     price       int unsigned not null comment '委托的价格',
 *     `status`    varchar(8)   not null default '待接单' comment '委托的状态',
 *     create_time datetime comment '发布时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (user_id) references userMessage (id) -- 联系用户表id
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entrust {
    private Integer id;//委托Id
    private Integer userId;//委托人id
    private String info;//委托的需求（文字）
    private String image;//委托的图片
    private String price;//委托的价格
    private String status;//委托的状态
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//更新时间
}
