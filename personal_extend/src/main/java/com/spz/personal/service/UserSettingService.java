package com.spz.personal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.personal.entity.UserSetting;

/**
* @author 86134
* @description 针对表【user_setting(用户的设置表)】的数据库操作Service
* @createDate 2024-09-25 17:15:32
*/
public interface UserSettingService extends IService<UserSetting> {

    void saveOrUpdateByUserId(UserSetting setting);
}
