package com.spz.pojo.communicate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
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
 *     mes_type    int          not null comment '消息类型',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (send_id) references userMessage (id),
 *     foreign key (receiver_id) references userMessage (id)
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {//实现Serializable接口，保证Message类的可序列化
    private static final long serialVersionUID = 1L;//保证其兼容性
    private Integer id; //ID
    private Integer senderId;//发送者id
    private Integer receiverId;//接收者id
    private String content;//内容
    private Integer mesType;//消息类型[可以在接口定义消息类型]
    private LocalDateTime createTime;//发布时间
    private LocalDateTime updateTime;//更新时间
}
