package com.spz.personal.service;


public interface ChangeUserService {

    void changeUserName(Integer id ,String username);


    void changePhone(Integer id ,String phone);


    void changePassword(Integer id ,String password);
}
