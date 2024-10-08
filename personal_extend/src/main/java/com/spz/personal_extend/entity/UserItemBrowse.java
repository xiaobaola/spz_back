package com.spz.personal_extend.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户与物品浏览关联表
 * @TableName user_item_browse
 */
@TableName(value ="user_item_browse")
@Data
public class UserItemBrowse implements Serializable {
    /**
     * 关联ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 物品ID
     */
    private Integer itemId;

    /**
     * 浏览次数
     */
    private Integer count;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}