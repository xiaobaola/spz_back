package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.user.User;
import com.spz.entity.wrapper.ChangeWrapper;
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
    public Res<String> ChangeUserName(@RequestBody ChangeWrapper changeWrapper){
        users = userMapper.getByAll();
        log.info("请求 user:{}", changeWrapper);
        Integer userId = changeWrapper.getUserId();
        String userName = changeWrapper.getUserName();
        for (User user:users){
            if (userName.equals(user.getUsername())){
                return Res.error("修改失败，用户名已存在");
            }
        }
        changeUserService.changeUserName(userId,userName);
        return Res.success("修改成功");
    }

    @PutMapping("/changePhone")
    public Res<String> ChangePhone(@RequestBody ChangeWrapper changeWrapper){
        users = userMapper.getByAll();
        log.info("请求 user:{}", changeWrapper);
        Integer userId = changeWrapper.getUserId();
        String phone = changeWrapper.getPhone();
        int count = 0;
        for(User user1:users){
            if(phone.equals(user1.getPhone())){
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
    public Res<String> ChangePassword(@RequestBody ChangeWrapper changeWrapper){
        log.info("请求 user:{}", changeWrapper);
        Integer userId = changeWrapper.getUserId();
        String password = changeWrapper.getPassword();
        changeUserService.changePassword(userId,password);
        return Res.success("修改成功");
    }

}
