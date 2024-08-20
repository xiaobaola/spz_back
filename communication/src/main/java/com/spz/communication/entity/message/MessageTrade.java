package com.spz.communication.entity.message;


/*
*
* create table message_trade(
    id int primary key auto_increment comment '交易信息ID',
    name varchar(100) default 'momo' comment '交易信息名',
    message varchar(500) not null comment '信息',
    trade_time_start datetime comment '交易开始时间',
    trade_time_finish datetime comment '交易结束时间',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间'
);
*
* */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "回收交易信息类")
public class MessageTrade {
    @Schema(description = "交易信息ID")
    private Integer id;
    @Schema(description = "交易信息名")
    private String name;
    @Schema(description = "信息")
    private String message;
    @Schema(description = "交易开始时间")
    private LocalDateTime tradeTimeStart;
    @Schema(description = "交易结束时间")
    private LocalDateTime tradeTimeFinish;
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
