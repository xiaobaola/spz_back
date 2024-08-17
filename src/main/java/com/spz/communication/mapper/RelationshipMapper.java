package com.spz.communication.mapper;

import com.spz.communication.entity.relationship.Relationship;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RelationshipMapper {

    @Select("select userId2 from relationship where userId1=#{userId1} and status=#{status}")
    List<Integer> getUserId2ByUserId1(Integer userId1,Integer status);

    @Select("select userId1 from relationship where userId2=#{userId2} and status=#{status}")
    List<Integer> getUserId1ByUserId2(Integer userId2,Integer status);

    @Select("select * from relationship where userId1=#{userId1} and userId2=#{userId2}")
    Relationship getByUserId1AndUserId2(Integer userId1, Integer userId2);

    @Update("update relationship set status=#{status}, greet=#{greet} where userId1=#{userId1} and userId2=#{userId2}")
    void updateStatusAndGreetByUserId1AndUserId2(Relationship relationship);

    @Insert("insert relationship(userId1, userId2, status, greet, update_time, create_time) " +
            "VALUES(#{userId1},#{userId2},#{status},#{greet},#{updateTime},#{createTime}) ")
    void insert(Relationship relationship);

    @Select("select * from relationship where userId1=#{userId}")
    List<Relationship> getUsersByUserId1(Integer userId);

    @Select("select userId2 from relationship where userId1=#{userId} and status=#{status}")
    List<Integer> getUserId2ByUserId1AndStatus(Integer userId, int status);

    @Select("select * from relationship where userId1=#{userId} and status=#{status}")
    List<Relationship> getByUserId1AndStatus(Integer userId, int status);

    @Update("update relationship set status=#{status} where userId1=#{userId1} and userId2=#{userId2}")
    void updateStatusByUserId1AndUserId2(Integer userId1, Integer userId2, int status);
}
