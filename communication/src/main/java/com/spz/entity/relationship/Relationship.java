package com.spz.entity.relationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {
    private Integer userId1; //用户id1
    private Integer userId2; //用户id2
    private Integer status; //用户间状态
    private String greet; //好友验证信息
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //更新时间
    /*
    * id int primary key auto_increment comment '单条关系唯一标识',
    * userId1 int          not null comment '用户id1',
    * userId2 int          not null comment '用户id2',
    * status int default 1 comment '用户间状态 0:取消通信权利 1:发送好友申请1->2 2:可以相互通信 3:发送好友申请2->1' ,
    * greet varchar(50) default '你好' comment '好友验证信息',
    * update_time datetime comment '更新时间',
    * create_time datetime comment '创建时间',
    * foreign key (userId1) references user (id),
    * foreign key (userId2) references user (id)
    * */
}
