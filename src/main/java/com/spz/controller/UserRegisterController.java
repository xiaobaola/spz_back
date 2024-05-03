package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.User;
import com.spz.mapper.UserMapper;
import com.spz.service.UserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/spz/user/register")
@Slf4j
public class UserRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public Res<String> UserRegisterInsert(@RequestBody User user){
        int count = 0;
        ArrayList<User> all = userMapper.getByAll();
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
        ArrayList<User> all = userMapper.getByAll();
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
        ArrayList<User> all = userMapper.getByAll();
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
