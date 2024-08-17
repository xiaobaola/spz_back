package com.spz.entity.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/02 19:43
 * Description:用户的身份信息
 * -- 用户信息表
 * create table userMessage
 * (
 *     id          int primary key auto_increment comment '用户id',
 *     username    varchar(20)  default 'momo' comment '用户名',
 *     phone       varchar(12) not null unique comment '电话号码',
 *     `password`  varchar(32) not null comment '密码',
 *     gender      tinyint unsigned comment '性别 , 1 男, 2 女',
 *     image       varchar(300) comment '图像',
 *     head_image  varchar(300) default '' comment '头像',
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间'
 * );
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
//    id           int primary key auto_increment comment '用户id',
//    username     varchar(20)          default 'momo' comment '用户名',
//    phone        varchar(12) not null unique comment '电话号码',
//    password     varchar(32) not null default '123456' comment '密码',
//    gender       tinyint unsigned comment '性别 , 1 男, 2 女',
//    image        varchar(300) comment '图像',
//    head_image   varchar(300)         default '' comment '头像',
//    introduction varchar(300) comment '自我介绍',
//    address      varchar(300) comment '简要地址',
//    update_time  datetime comment '更新时间',
//    create_time  datetime comment '创建时间'
    private Integer id; //ID
    private String username; //用户名
    private String phone;//电话
    private String password; //密码
    private Short gender; //性别 , 1 男, 2 女
    private String image; //图像
    private String headImage;//头像
    private String introduction; //自我介绍
    private String address; //简要地址
    private LocalDateTime updateTime;//更新时间
    private LocalDateTime createTime; //创建时间

    public static int getUserIdBySession(int userId, HttpServletRequest request) {
        // try
        User user = (User) request.getSession().getAttribute("user");
        if(user != null && user.getId() != null) {
            userId = user.getId();
        }
        return  userId;
    }

}
