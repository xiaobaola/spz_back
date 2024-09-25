package com.spz.personal_extend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spz.personal_extend.service.UserSettingService;

@RequestMapping("/spz/user/setting")
@RestController
@Slf4j
@Tag(name = "用户设置")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingService settingService;


}
