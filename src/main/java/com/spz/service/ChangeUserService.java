package com.spz.service;


import com.spz.entity.user.User;
import org.apache.ibatis.annotations.Update;

public interface ChangeUserService {

    void changeUserName(Integer id ,String username);


    void changePhone(Integer id ,String phone);


    void changePassword(Integer id ,String password);
}
