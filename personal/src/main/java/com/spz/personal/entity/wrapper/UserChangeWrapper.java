package com.spz.personal.entity.wrapper;


import lombok.Data;

@Data
public class UserChangeWrapper {
    private Integer userId;
    private String userName;
    private String phone;
    private String password;
}