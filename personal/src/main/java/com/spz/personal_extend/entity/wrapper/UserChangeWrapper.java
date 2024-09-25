package com.spz.personal_extend.entity.wrapper;


import lombok.Data;

@Data
public class UserChangeWrapper {
    private Integer userId;
    private String userName;
    private String phone;
    private String password;
}
