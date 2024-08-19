package com.spz.communication.service;

import com.spz.communication.entity.relationship.Relationship;

import java.util.List;

public interface RelationshipService {
    void addRelationship(Relationship relationship);

    void changeStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status);

    List<Integer> getUserId2sByUserId1AndStatus(Integer userId, int status);

    List<Relationship> getListByUserId1(Integer userId);

    List<Relationship> getListByUserId1AndStatus(Integer userId, int status);
}
