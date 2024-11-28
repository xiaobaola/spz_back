package com.spz.communication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spz.communication.entity.dto.RelationshipDto;
import com.spz.communication.entity.relationship.Relationship;

import java.util.List;

public interface RelationshipService extends IService<Relationship> {
    void addRelationship(Relationship relationship);

    void changeStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status);

    List<Integer> getUserId2sByUserId1AndStatus(Integer userId, int status);

    List<Relationship> getListByUserId1(Integer userId);

    List<Relationship> getListByUserId1AndStatus(Integer userId, int status);

    List<RelationshipDto> listRelationShipDtoByStatus(int status);

    void change2StatusBy2UserId(Integer userId1, Integer userId2, Integer status1, Integer status2);
}
