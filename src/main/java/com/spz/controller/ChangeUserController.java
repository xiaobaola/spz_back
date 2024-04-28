package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.User;
import com.spz.mapper.UserMapper;
import com.spz.service.ChangeUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/spz/user")
@Slf4j
public class ChangeUserController {

    @Autowired
    private ChangeUserService changeUserService;

    @Autowired
    private UserMapper userMapper;

    ArrayList<User> users;

    @PutMapping("/changeUserName")
    public Res<String> ChangeUserName(@RequestParam Integer userId,@RequestParam String userName){
        users = userMapper.getByAll();
        log.info("请求 userId:{}, userName:{}", userId, userName);
        for (User user:users){
            if (userName.equals(user.getUsername())){
                return Res.error("修改失败，用户名已存在");
            }
        }
        changeUserService.changeUserName(userId,userName);
        return Res.success("修改成功");
    }

    @PutMapping("/changePhone")
    public Res<String> ChangePhone(@RequestParam Integer userId,@RequestParam String phone){
        users = userMapper.getByAll();
        log.info("请求 userId:{}, phone:{}", userId, phone);
        int count = 0;
        for(User user:users){
            if(phone.equals(user.getPhone())){
                count++;
                if (count == 3){
                    return Res.error("修改失败，电话号码已存在三次");
                }
            }
        }
        changeUserService.changePhone(userId,phone);
        return Res.success("修改成功");
    }

    @PutMapping("/changePassword")
    public Res<String> ChangePassword(@RequestParam Integer userId,@RequestParam String password){
        log.info("请求 userId:{}, password:{}", userId, password);
        changeUserService.changePassword(userId,password);
        return Res.success("修改成功");
    }

}
