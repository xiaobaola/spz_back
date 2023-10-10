package com.spz.pojo.scrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User:我吃饭的时候不饿
 * Date: 2023/10/10 10:28
 * Description:废品类型表
 * -- 废品类型表
 * create table scrapType
 * (
 *     id          int primary key,
 *     `name`        varchar(30) not null,
 *     create_time datetime comment '创建时间',
 *     update_time datetime comment '更新时间'
 * );
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapType {
    private Integer id;//主键id
    private String name;//废品类型名
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime;//更新时间
}
