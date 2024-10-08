package com.spz.personal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户的设置表
 * @TableName user_setting
 */
@TableName(value ="user_setting")
@Data
public class UserSetting implements Serializable {

    /**
     * 用户ID 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 是否开启消息提醒 0:关闭 1:开启
     */
    private Integer remind;

    /**
     * 是否开启消息推送 0:关闭 1:开启
     */
    private Integer message;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}