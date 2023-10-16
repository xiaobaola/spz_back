package com.spz.entity.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    private Integer id; //管理员唯一标识
    private String username; //管理员用户名
    private String password; //密码
    private LocalDateTime updateTime; //更新时间
    private LocalDateTime createTime; //创建时间
}
