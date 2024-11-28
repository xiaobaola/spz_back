package com.spz.communication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spz.communication.entity.dto.RelationshipDto;
import com.spz.communication.mapper.RelationshipMapper;
import com.spz.communication.service.RelationshipService;
import com.spz.communication.entity.relationship.Relationship;
import com.spz.personal.entity.User;
import com.spz.personal.service.impl.UserServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// mybatisPlus
@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl extends ServiceImpl<RelationshipMapper, Relationship> implements RelationshipService{

    private RelationshipMapper relationshipMapper;
    @Autowired
    public void setRelationshipMapper(RelationshipMapper relationshipMapper) {
        this.relationshipMapper = relationshipMapper;
    }

    private final UserServiceImpl userService;

    @Override
    public void addRelationship(Relationship relationship) {
        //判断是否在数据库中存在此数据
        //有，则更新此条数据
        //无，则插入此条新数据
        Relationship r1 = relationshipMapper.selectByUserId1AndUserId2(relationship.getUserId1(), relationship.getUserId2());
        if(r1 != null) {
            r1.setStatus(4);
            r1.setGreet(relationship.getGreet());
            r1.setUpdateTime(LocalDateTime.now());
            relationshipMapper.updateStatusAndGreetByUserId1AndUserId2(r1);
        } else {
            //补充完整
            relationship.setStatus(4);
            relationship.setCreateTime(LocalDateTime.now());
            relationship.setUpdateTime(LocalDateTime.now());
            relationshipMapper.insertRelationship(relationship);
        }
        Integer userId1 = relationship.getUserId1();
        Integer userId2 = relationship.getUserId2();
        relationship.setUserId1(userId2);
        relationship.setUserId2(userId1);
        Relationship r2 = relationshipMapper.selectByUserId1AndUserId2(relationship.getUserId1(), relationship.getUserId2());
        if(r2 != null) {
            r2.setStatus(5);
            r2.setGreet(relationship.getGreet());
            r2.setUpdateTime(LocalDateTime.now());
            relationshipMapper.updateStatusAndGreetByUserId1AndUserId2(r2);
        } else {
            //补充完整
            relationship.setStatus(5);
            relationship.setCreateTime(LocalDateTime.now());
            relationship.setUpdateTime(LocalDateTime.now());
            relationshipMapper.insertRelationship(relationship);
        }

    }

    @Override
    public void changeStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status) {
        relationshipMapper.updateStatusByUserId1AndUserId2(userId1,userId2,status);
        relationshipMapper.updateStatusByUserId1AndUserId2(userId2,userId1,status);
    }

    @Override
    public List<Integer> getUserId2sByUserId1AndStatus(Integer userId, int status) {
        return relationshipMapper.selectUserId2sByUserId1AndStatus(userId, status);
    }

    @Override
    public List<Relationship> getListByUserId1(Integer userId) {
        return relationshipMapper.selectListByUserId1(userId);
    }

    @Override
    public List<Relationship> getListByUserId1AndStatus(Integer userId, int status) {
        return relationshipMapper.selectListByUserId1AndStatus(userId, status);
    }

    @Override
    public List<RelationshipDto> listRelationShipDtoByStatus(int status) {
        // 1.根据status 获取所有的relationship信息
        List<Relationship> relationships = relationshipMapper.selectListByStatus(status);
        // 2.遍历relationship，对relationship增强，补全username1,username2信息
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        for(Relationship relationship : relationships) {
            // 2.0 创建relationshipDto
            RelationshipDto relationshipDto = new RelationshipDto();
            // 2.0.1 对象拷贝
            BeanUtils.copyProperties(relationship,relationshipDto);
            // 2.1 通过userId获取user信息
            User user1 =  userService.getById(relationship.getUserId1());
            User user2 =  userService.getById(relationship.getUserId2());
            // 2.2 补全username1,username2信息
            relationshipDto.setUsername1(user1.getUsername());
            relationshipDto.setUsername2(user2.getUsername());
            // 2.3 放入list中
            relationshipDtos.add(relationshipDto);
        }
        return relationshipDtos;
    }

    @Override
    public void change2StatusBy2UserId(Integer userId1, Integer userId2, Integer status1, Integer status2) {
        // 可以做一个安全校验 优化
        relationshipMapper.updateStatusByUserId1AndUserId2(userId1,userId2,status1);
        relationshipMapper.updateStatusByUserId1AndUserId2(userId2,userId1,status2);
    }

}
