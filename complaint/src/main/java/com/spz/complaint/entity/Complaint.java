package com.spz.complaint.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 投诉表
 * </p>
 *
 * @author last
 * @since 2024-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("complaint")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 投诉表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 投诉编号
     */
    private String number;

    /**
     * 投诉人id
     */
    private Integer complainant;

    /**
     * 被投诉人id
     */
    private Integer respondent;

    /**
     * 客服id
     */
    private Integer clerk;

    /**
     * 状态 0:未处理 1:已跟踪 2:已与投诉人沟通 3:已反馈被投诉人 4:已反馈投诉人 5:已处理
     */
    private Integer status;

    /**
     * json格式内容
     */
    private String data;

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
