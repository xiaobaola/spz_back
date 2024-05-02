package com.spz.entity.wrapper;


import lombok.Data;

@Data
public class ChangeWrapper {
    private Integer userId;
    private String userName;
    private String phone;
    private String password;
}
