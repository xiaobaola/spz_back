package com.spz.recycle.entity;



import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/07 18:39
 * Description:废品类
 * -- 废品表
 * create table scrap
 * (
 *     id          int primary key auto_increment comment '废品id',
 *     user_id      int         not null comment '用户id',
 *     `name`      varchar(20) not null comment '废品名称',
 *     image       varchar(300) comment '物品图片',
 *     scarp_type  varchar(20) comment '废品类型',
 *     type        int default 0 comment '描述类型 0计量描述 1度量单位 2其他描述',
 *     `count`     varchar(20) comment '0 计量描述 如1-5瓶 5-10斤',
 *     size        varchar(20) comment '1 度量单位 度量单位或类型 kg/斤/g',
 *     other       varchar(20) comment '2其他描述 其他详细或限制描述',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间'
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class
Scrap {
    private Integer id;//废品id
    private String name;//废品名字
    private Integer price;//废品价格
    private String image;//物品图片
    private Integer scrapTypeId;//废品类型
    private Integer type;//物品单位描述类型  0计量描述 1度量单位 2其他描述
    private Integer count;//服务于前端
    private String size;//1 度量单位 度量单位或类型 kg/斤/g
    private String other;//2其他描述 其他详细或限制描述
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//更新时间
}
