package com.spz.service;

import com.spz.entity.relationship.Relationship;

public interface RelationshipService {
    void addRelationship(Relationship relationship);

    void changeStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status);
}
