package com.spz.entity.useraddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 20:01
 * Description:用户的ip地址信息
 * -- 用户地址表
 * create table address
 * (
 *     user_id         int comment '用户id',
 *     address_ip varchar(50) not null comment '用户ip地址',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间',
 *     foreign key (user_id) references userMessage (id) -- 联系用户表id
 * );
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private Integer userId;//联系用户id
    private String addressIp;//用户ip地址
    private LocalDateTime createTime;//发布时间
    private LocalDateTime updateTime;//更新时间
}
