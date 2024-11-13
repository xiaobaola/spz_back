package com.spz.secondHand.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 二手物品审核表
 * </p>
 *
 * @author last
 * @since 2024-11-13
 */

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

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("second_hand_item_reject")
public class SecondHandItemReject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 审核表编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 二手物品id
     */
    private Integer itemId;

    /**
     * 管理员id
     */
    private Integer managerId;

    /**
     * 二手物品描述
     */
    private String message;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
