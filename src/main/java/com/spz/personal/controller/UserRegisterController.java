package com.spz.personal.controller;

import com.spz.personal.service.UserService;
import com.spz.public_resource.common.Res;
import com.spz.personal.entity.User;
import com.spz.personal.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

// 20240715用户注册
@RestController
@RequestMapping("/spz/user/register")
@Slf4j
public class UserRegisterController {


    private UserRegisterService userRegisterService;

    private UserService userService;

    @Autowired
    public void setUserRegisterService(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Res<String> UserRegisterInsert(@RequestBody User user){
        int count = 0;
        ArrayList<User> all = userService.getByAll();
        for (User e:all){
            if (user.getUsername().equals(e.getUsername())){
                return Res.error("注册失败,用户名重复");
            }
            if (user.getPhone().equals(e.getPhone())){
                count++;
                if (count == 3){
                    return Res.error("注册失败,电话号码已注册超过三次");
                }
            }
        }
        userRegisterService.userRegister(user);
        return Res.success("注册成功");
    }

    @GetMapping
    public Res<Integer> UserRegisterSelectId(@RequestParam String username){
        log.info("UserRegisterSelectId,参数:{}",username);
        return Res.success(userRegisterService.getIdByUserName(username));
    }

    @GetMapping("/u")
    public Res<Integer> UserNameCount(@RequestParam String username){
        log.info("UserNameCount,参数:{}",username);
        ArrayList<User> all = userService.getByAll();
        for (User e:all){
            if (username.equals(e.getUsername())){
                return Res.success(0);
            }
        }
        return Res.success(1);
    }

    @GetMapping("/p")
    public Res<Integer> PhoneCount(@RequestParam String phone){
        int count = 0;
        ArrayList<User> all = userService.getByAll();
        for (User e:all){
            if (phone.equals(e.getPhone())){
                count++;
                if (count == 3){
                    return Res.success(0);
                }
            }
        }
        return Res.success(1);
    }
}
