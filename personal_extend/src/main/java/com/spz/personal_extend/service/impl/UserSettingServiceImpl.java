package com.spz.personal_extend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.personal_extend.mapper.UserSettingMapper;
import org.springframework.stereotype.Service;
import com.spz.personal_extend.entity.UserSetting;
import com.spz.personal_extend.service.UserSettingService;

/**
* @author 86134
* @description 针对表【user_setting(用户的设置表)】的数据库操作Service实现
* @createDate 2024-09-25 17:15:32
*/
@Service
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSetting>
    implements UserSettingService {

    @Override
    public void saveOrUpdateByUserId(UserSetting setting) {
        LambdaQueryWrapper<UserSetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserSetting::getUserId, setting.getUserId());
        saveOrUpdate(setting, queryWrapper);
    }
}




