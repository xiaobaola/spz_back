package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.dto.UserDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.relationship.Relationship;
import com.spz.entity.user.User;
import com.spz.mapper.RelationshipMapper;
import com.spz.service.CommunicationUserService;
import com.spz.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunicationUserServiceImpl implements CommunicationUserService {
    @Autowired
    UserService userService;

    @Autowired
    RelationshipMapper relationshipMapper;


    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<User> userList = userService.list(username, begin, end);

        //为了获取total
        Page<User> userPage = (Page<User>) userList;

        //3、封装pageBean对象
        PageBean pageBean = new PageBean(userPage.getTotal(), userPage.getResult());

        return pageBean;
    }

    @Override
    public List<UserDto> getUserDtoListByInfo(String info, Integer userId) {
        List<UserDto> userDtoList = new ArrayList<>();
        //空判断
        if (info.isEmpty()) {
            return userDtoList;
        }
        List<User> users = userService.getByLikeUsername(info);
        //使用正则表达式匹配, 是否全为数字
        if(info.matches("\\d+")) {
            users.add(userService.getByPhone(info));
            if (info.length() < 9)
            users.add(userService.getById(Integer.parseInt(info)));
        }
        users = users.stream()
                .filter(user -> user != null && StringUtils.isNotBlank(user.getUsername())) // 过滤掉null和username为空的User
                .distinct() // 去除剩余元素中的重复项（如果User类重写了equals和hashCode方法）
                .collect(Collectors.toCollection(ArrayList::new)); // 收集到新的ArrayList中
        // 排序
        List<Relationship> relationships = relationshipMapper.getUsersByUserId1(userId);
        // 封装成dto
        for(User user : users) {
            UserDto userDto =  new UserDto();
            //对象拷贝
            BeanUtils.copyProperties(user, userDto);
            userDto.setStatus(0);
            for (Relationship r : relationships) {
                if(user.getId().equals(r.getUserId2())) {
                    userDto.setStatus(r.getStatus());
                    userDto.setGreet(r.getGreet());
                }
            }
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> getUserDtoListByUserId(Integer userId) {
        List<UserDto> userDtoList = new ArrayList<>();
        //1.在关系中查询userId1 = userid and status = 3 的userId2s
        List<Relationship> relationships = relationshipMapper.getByUserId1AndStatus(userId, 3);
        if(relationships.isEmpty()) {
            return userDtoList;
        }
        List<Integer> userId2s = new ArrayList<>();
        for(Relationship r : relationships) {
            userId2s.add(r.getUserId2());
        }
        //2.根据userId2s 查询user
        List<User> users = userService.getUsersByUserIds(userId2s);
        //3.补充完整需要的Dto
//        System.out.println(users);
        for (User user : users) {
            UserDto userDto = new UserDto();
            //对象拷贝
            BeanUtils.copyProperties(user, userDto);
            //3.1 获取greet
            for(Relationship r : relationships) {
                if(user.getId().equals(r.getUserId2())) {
                    userDto.setGreet(r.getGreet());
                }
            }
            userDtoList.add(userDto);
        }
        //4.返回List
        return userDtoList;
    }
}
