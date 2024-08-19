package com.spz.communication.entity.message;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:56
 * Description:通讯的信息类
 * -- 通讯表
 * create table communicate
 * (
 *     id          int primary key auto_increment comment '通讯id',
 *     send_id     int          not null comment '发送者id',
 *     receiver_id int          not null comment '接收者id',
 *     content     varchar(300) not null comment '内容',
 *     mes_status    tinyint          not null comment '消息类型',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (send_id) references userMessage (id),
 *     foreign key (receiver_id) references userMessage (id)
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUser {
    private Integer id; //ID
    private Integer senderId;//发送者id
    private Integer receiverId;//接收者id
    private String message;//内容
    private Short mesStatus;//消息类型[可以在接口定义消息类型]
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;//发布时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;//更新时间
}
