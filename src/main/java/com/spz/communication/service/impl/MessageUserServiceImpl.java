package com.spz.communication.service.impl;

import com.spz.communication.entity.message.MessageUser;
import com.spz.communication.mapper.MessageUserMapper;
import com.spz.communication.service.MessageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageUserServiceImpl implements MessageUserService {

    MessageUserMapper messageUserMapper;

    @Autowired
    public void setMessageUserMapper(MessageUserMapper messageUserMapper) {
        this.messageUserMapper = messageUserMapper;
    }

    @Override
    public List<MessageUser> getList(Integer userId1, Integer userId2) {

        // 根据userId1查询相关信息
        // 查询sendId == userId1 && receiverId == userId2
        List<MessageUser> users1 = messageUserMapper.selectBySendIdAndReceiverId(userId1,userId2);
        // 创建一个list
        List<MessageUser> users = new ArrayList<>(users1);
        // 查询sendId == userId2 && receiverId == userId1
        List<MessageUser> users2 = messageUserMapper.selectBySendIdAndReceiverId(userId2,userId1);
        users.addAll(users2);
        // 根据时间排序
        users.sort(Comparator.comparing(MessageUser::getCreateTime));
        return users;
    }

    @Override
    public void add(MessageUser messageUser) {
        messageUser.setCreateTime(LocalDateTime.now());
        messageUser.setUpdateTime(LocalDateTime.now());
        messageUser.setMesStatus((short)1);
        messageUserMapper.insert(messageUser);
    }
}
