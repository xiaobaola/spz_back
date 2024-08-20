package com.spz.recycle.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:47
 * Description:关于记录废品交易量
 * -- 废品交易表
 * create table scrapTrap
 * (
 *     id     int primary key auto_increment,
 *     user_id      int        not null comment '用户id',
 *     scrap_id     int comment '废品id',
 *     numbers     int        not null comment '数量',
 *     image       varchar(80) comment '物品图片',
 *     `status`    varchar(8) not null default '未交易' comment '废品交易状态',
 *     type        int                 default 0 comment '描述类型 0计量描述 1度量单位 2其他描述',
 *     `count`     varchar(20) comment '0 计量描述 如1-5瓶 5-10斤',
 *     size        varchar(20) comment '1 度量单位 度量单位或类型 kg/斤/g',
 *     other       varchar(20) comment '2其他描述 其他详细或限制描述',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (user_id) references userMessage (id),-- 联系用户表的id
 *     foreign key (scrap_id) references scrap (id)      -- 联系废品表的sid
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapTrade {
    private Integer id;//交易Id
    private String number; //订单编号
    private Integer userId;//用户Id
    private String consignee; //交易员名称
//    private String userName; //用户名
    private Integer status;//回收品交易状态 0: 上传 1: 接单 2: 确认 3: 完成 4: 取消
    private String address; //用户地址
    private String phone; //用户手机号
    private Integer price; //交易总金额
    private String image;//物品图片
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//更新时间
}
