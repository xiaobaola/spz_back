package com.spz.personal_extend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的设置表
 * @TableName user_setting
 */
@TableName(value ="user_setting")
@Data
public class UserSetting implements Serializable {
    /**
     * 设置ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 是否开启消息提醒 0:关闭 1:开启
     */
    private Integer remind;

    /**
     * 是否开启消息推送 0:关闭 1:开启
     */
    private Integer message;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}