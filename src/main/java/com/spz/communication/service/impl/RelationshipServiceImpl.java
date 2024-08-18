package com.spz.communication.service.impl;

import com.spz.communication.entity.relationship.Relationship;
import com.spz.communication.mapper.RelationshipMapper;
import com.spz.communication.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    private RelationshipMapper relationshipMapper;
    @Autowired
    public void setRelationshipMapper(RelationshipMapper relationshipMapper) {
        this.relationshipMapper = relationshipMapper;
    }

    @Override
    public void addRelationship(Relationship relationship) {
        //判断是否在数据库中存在此数据
        //有，则更新此条数据
        //无，则插入此条新数据
        Relationship r1 = relationshipMapper.selectByUserId1AndUserId2(relationship.getUserId1(), relationship.getUserId2());
        if(r1 != null) {
            r1.setStatus(1);
            r1.setGreet(relationship.getGreet());
            r1.setUpdateTime(LocalDateTime.now());
            relationshipMapper.updateStatusAndGreetByUserId1AndUserId2(r1);
        } else {
            //补充完整
            relationship.setStatus(1);
            relationship.setCreateTime(LocalDateTime.now());
            relationship.setUpdateTime(LocalDateTime.now());
            relationshipMapper.insert(relationship);
        }
        Integer userId1 = relationship.getUserId1();
        Integer userId2 = relationship.getUserId2();
        relationship.setUserId1(userId2);
        relationship.setUserId2(userId1);
        Relationship r2 = relationshipMapper.selectByUserId1AndUserId2(relationship.getUserId1(), relationship.getUserId2());
        if(r2 != null) {
            r2.setStatus(3);
            r2.setGreet(relationship.getGreet());
            r2.setUpdateTime(LocalDateTime.now());
            relationshipMapper.updateStatusAndGreetByUserId1AndUserId2(r2);
        } else {
            //补充完整
            relationship.setStatus(3);
            relationship.setCreateTime(LocalDateTime.now());
            relationship.setUpdateTime(LocalDateTime.now());
            relationshipMapper.insert(relationship);
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
    public List<Relationship> getUsersByUserId1(Integer userId) {
        return relationshipMapper.selectUsersByUserId1(userId);
    }

    @Override
    public List<Relationship> getListByUserId1AndStatus(Integer userId, int status) {
        return relationshipMapper.selectListByUserId1AndStatus(userId, status);
    }
}
