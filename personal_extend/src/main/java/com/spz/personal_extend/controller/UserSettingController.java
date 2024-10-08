package com.spz.personal_extend.controller;

import com.spz.common.Res;
import com.spz.personal_extend.entity.UserSetting;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.spz.personal_extend.service.UserSettingService;

@RequestMapping("/spz/user/setting")
@RestController
@Slf4j
@Tag(name = "用户设置")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingService settingService;

    //get请求 获取用户设置
    @GetMapping
    public Res<UserSetting> getUserSetting(@RequestParam Integer userId) {
        return Res.success(settingService.getById(userId));
    }

    // post请求 插入或更新用户设置
    @PostMapping
    public Res<UserSetting> saveUserSetting(@RequestBody UserSetting setting) {
        log.info("setting:{}", setting);
        // userId 需要做安全处理
        settingService.saveOrUpdate(setting);
//        settingService.saveOrUpdateByUserId(setting);
        return Res.success(setting);
    }
}
