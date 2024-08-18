package com.spz.service;


public interface UserChangeUserService {

    void changeUserName(Integer id ,String username);


    void changePhone(Integer id ,String phone);


    void changePassword(Integer id ,String password);
}
