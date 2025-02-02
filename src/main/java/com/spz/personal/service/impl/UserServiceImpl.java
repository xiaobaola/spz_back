package com.spz.personal.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.communication.service.RelationshipService;
import com.spz.personal.entity.UserDto;
import com.spz.public_resource.entity.page.PageBean;
import com.spz.communication.entity.relationship.Relationship;
import com.spz.personal.entity.User;
import com.spz.personal.mapper.UserMapper;
import com.spz.personal.service.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private RelationshipService relationshipService;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRelationshipService(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @Override
    public User getByUsernameAndPassword(User user) {
//        ArrayList<UserMessage> userMessageArrayList = userMessageMapper.getByAll();
        return userMapper.getByInfo(user);
    }

    @Override
    public void updateById(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String username, LocalDate begin, LocalDate end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<User> userList = userMapper.list(username, begin, end);

        //为了获取total
        Page<User> userPage = (Page<User>) userList;

        //3、封装pageBean对象
        return new PageBean(userPage.getTotal(), userPage.getResult());
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void insert(User user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public List<UserDto> getUserDtoListByInfo(String info, Integer userId) {
        List<UserDto> userDtoList = new ArrayList<>();
        //空判断
        if (info.isEmpty()) {
            return userDtoList;
        }
        List<User> users = userMapper.selectListByLikeUsername(info);
        //使用正则表达式匹配, 是否全为数字
        if(info.matches("\\d+")) {
            users.add(userMapper.selectByPhone(info));
            if (info.length() < 9) {
                users.add(userMapper.selectById(Integer.parseInt(info)));
            }
        }
        users = users.stream()
                .filter(user -> user != null && StringUtils.isNotBlank(user.getUsername())) // 过滤掉null和username为空的User
                .distinct() // 去除剩余元素中的重复项（如果User类重写了equals和hashCode方法）
                .collect(Collectors.toCollection(ArrayList::new)); // 收集到新的ArrayList中
        // 排序
        List<Relationship> relationships = relationshipService.getUsersByUserId1(userId);
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
        List<Relationship> relationships = relationshipService.getListByUserId1AndStatus(userId, 3);
        if(relationships.isEmpty()) {
            return userDtoList;
        }
        List<Integer> userId2s = new ArrayList<>();
        for(Relationship r : relationships) {
            userId2s.add(r.getUserId2());
        }
        //2.根据userId2s 查询user
        List<User> users = userMapper.getUsersByUserIds(userId2s);
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

    @Override
    public ArrayList<User> getByAll() {
        return userMapper.selectByAll();
    }
}
