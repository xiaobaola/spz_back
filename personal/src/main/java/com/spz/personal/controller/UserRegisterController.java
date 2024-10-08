package com.spz.personal.controller;

import com.spz.personal.entity.User;
import com.spz.personal.service.UserService;
import com.spz.common.Res;
import com.spz.personal.service.UserRegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

// 20240715用户注册
@RestController
@RequestMapping("/spz/user/register")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户注册模块")
public class UserRegisterController {


    private final UserRegisterService userRegisterService;

    private final UserService userService;


    @PostMapping
    public Res<String> UserRegisterInsert(@RequestBody User user){
        int count = 0;
        ArrayList<User> all = userService.getList();
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
        userRegisterService.add(user);
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
        ArrayList<User> all = userService.getList();
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
        ArrayList<User> all = userService.getList();
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
