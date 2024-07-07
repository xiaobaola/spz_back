package com.spz.entity.safe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer userId;
    private String addressIp;
    private Integer access;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
