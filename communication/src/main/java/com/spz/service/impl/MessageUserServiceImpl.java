package com.spz.service.impl;

import com.spz.entity.message.MessageUser;
import com.spz.entity.user.User;
import com.spz.mapper.MessageUserMapper;
import com.spz.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageUserServiceImpl implements MessageUserService {
    @Autowired
    MessageUserMapper messageUserMapper;

    @Override
    public List<MessageUser> list(Integer userId1, Integer userId2) {
        // 创建一个list
        List<MessageUser> users = new ArrayList<>();
        // 根据userId1查询相关信息
        // 查询sendId == userId1 && receiverId == userId2
        List<MessageUser> users1 = messageUserMapper.getBySendIdAndReceiverId(userId1,userId2);
        users.addAll(users1);
        // 查询sendId == userId2 && receiverId == userId1
        List<MessageUser> users2 = messageUserMapper.getBySendIdAndReceiverId(userId2,userId1);
        users.addAll(users2);
        // 根据时间排序
        users.sort(Comparator.comparing(MessageUser::getCreateTime));
        return users;
    }

    @Override
    public void insert(MessageUser messageUser) {
        messageUser.setCreateTime(LocalDateTime.now());
        messageUser.setUpdateTime(LocalDateTime.now());
        messageUser.setMesStatus((short)1);
        messageUserMapper.insert(messageUser);
    }
}
