package com.spz.entity.manage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
    private Integer id;
    private String username;
    private String password;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
