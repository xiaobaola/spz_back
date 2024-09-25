package com.spz.personal_extend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.api.weixin.WechatApi;
import com.spz.common.ExceptionMessageConstant;
import com.spz.common.GlobalException;
import com.spz.entity.page.PageBean;
import com.spz.personal_extend.entity.User;
import com.spz.personal_extend.mapper.UserMapper;
import com.spz.personal_extend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final WechatApi wechatApi;


    @Override
    public User getByUsernameAndPassword(String username,String password) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMapper.selectByUsernameAndPassword(username,password);
    }

    @Override
    public void changeById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateUserById(user);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<User> userList = userMapper.selectByUsernameOrBeginAndEnd(username, begin, end);

        //为了获取total
        Page<User> userPage = (Page<User>) userList;

        //3、封装pageBean对象
        return new PageBean(userPage.getTotal(), userPage.getResult());
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    public void insertUser(User user) {
        userMapper.insertWithUsernameAndPhoneAndGender(user);
    }

    @Override
    public ArrayList<User> getList() {
        return userMapper.selectByAll();
    }

    @Override
    public List<User> getByLikeUsername(String info) {
        return userMapper.selectListByLikeUsername(info);
    }

    @Override
    public User getByPhone(String info) {
        return userMapper.selectByPhone(info);
    }

    @Override
    public List<User> getListByUsernameOrBeginAndEnd(String username, LocalDate begin, LocalDate end) {
        return userMapper.selectByUsernameOrBeginAndEnd(username, begin, end);
    }

    @Override
    public List<User> getUsersByUserIds(List<Integer> userId2s) {
        return userMapper.selectUsersByUserIds(userId2s);
    }

    @Override
    public User wxLogin(String code) {
        // 将数据处理逻辑写到controller中
        // 1 从授权码中获取openid 等数据
        // 1.1 调用api模块中的微信登录接口
        String openid = wechatApi.getOpenIdByCode(code);
        log.info("openid:{}", openid);
        // 2 数据处理 null 返回错误信息 用户未授权失败
        if (openid == null) {
            throw new GlobalException(ExceptionMessageConstant.LOGIN_FAILURE_USER);
        }
        // 业务处理逻辑写到service中
        // 1.1 通过openid查询用户
        User user = userMapper.selectByOpenId(openid);
        // 1 如果openid在数据库中不存在，则创建用户
        if (user == null) {
            user = new User();
            // 用户后期自主配置 用户名和密码
            user.setOpenId(openid);
            userMapper.insert(user);
        }
        // 2 如果openid存在，则查询用户id
        return userMapper.selectByOpenId(openid);
    }

}
