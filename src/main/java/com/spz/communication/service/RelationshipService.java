package com.spz.communication.service;

import com.spz.communication.entity.relationship.Relationship;

public interface RelationshipService {
    void addRelationship(Relationship relationship);

    void changeStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status);
}
