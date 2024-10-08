package com.spz.communication.controller;

import com.spz.common.Res;
import com.spz.communication.entity.dto.MessageUserDto;
import com.spz.communication.entity.relationship.Relationship;
import com.spz.communication.service.CommunicationUserService;
import com.spz.communication.service.MessageTradeService;
import com.spz.communication.service.RelationshipService;
import com.spz.personal.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/spz/user")
@RestController
@Slf4j
@Tag(name = "用户交友模块")
public class CommunicationUserController {
    @Autowired
    private CommunicationUserService communicationUserService;

    @Autowired
    private MessageTradeService messageTradeService;

    @Autowired
    private RelationshipService relationshipService;

    /**
     * Author last
     * Param @param userId 用户 ID
     *
     * @param request 请求
     *                Return @return {@link Res }<{@link List }<{@link User }>>
     *                Describe 按用户 ID 获取用户
     *///获取好友列表 采用session获取userId,方法封装在user中
    @GetMapping("/friend")
    public Res<List<User>> getUserByUserId(@RequestParam Integer userId,HttpServletRequest request){
        // 20240808安全优化 session->userid
        userId = User.getUserIdBySession(userId,request);
        log.info("get 好友列表, userId:{}",userId);
        return Res.success(messageTradeService.getUsersByUserId(userId));
    }

    /**
     * Author last
     * Param @param relationship 关系
     *
     * @param request 请求
     *                Return @return {@link Res }<{@link String }>
     *                Describe 添加关系
     */
    @PostMapping("/friend/add")
    public Res<String> addRelationship(@RequestBody Relationship relationship, HttpServletRequest request) {
        // 没有必要缓存，用户不同结果不同，请求不频繁
        // 20240809安全优化 session->userId 可能存在参数风险，自己一定要是userId1才行
        int userId = relationship.getUserId1();
        userId = User.getUserIdBySession(userId,request);
        relationship.setUserId1(userId);

        log.info("添加好友申请, 参数{}", relationship);
        relationshipService.addRelationship(relationship);
//        return Res.error("发送申请失败");
        return Res.success("发送申请成功");
    }

    @GetMapping("/search")
    public Res<List<MessageUserDto>> searchUserDtoListByInfo(@RequestParam String info, @RequestParam Integer userId, HttpServletRequest request){
        // 可以考虑做缓存，用户不同结果不同，但可以利用参数做拼接，请求频繁时可以做缓存，但要确保数据一致性
        // 20240809安全优化 session->userId
        userId = User.getUserIdBySession(userId,request);
        log.info("用户搜索, 参数:{},{}",info,userId);
        List<MessageUserDto> messageUserDtoListByInfo = communicationUserService.getUserDtoListByInfo(info, userId);
        log.info("返回,{}", messageUserDtoListByInfo);
        return Res.success(messageUserDtoListByInfo);
    }

    @GetMapping("/newFriendList")
    public Res<List<MessageUserDto>> getUserDtoListByUserId(@RequestParam Integer userId, HttpServletRequest request) {
        // 不需要缓存，用户不同结果不同，请求不频繁
        // 20240809安全优化 session->userId
        userId = User.getUserIdBySession(userId,request);
        log.info("查询好友验证, 参数{}", userId);
        return Res.success(communicationUserService.getUserDtoListByUserId(userId));
    }
    @PostMapping("/agree")
    public Res<String> postAgree(@RequestBody Relationship relationship, HttpServletRequest request) {
        Integer userId1 = relationship.getUserId1();
        // 20240809安全优化 session->userId
        userId1 = User.getUserIdBySession(userId1,request);
        Integer userId2 = relationship.getUserId2();
        log.info("同意好友申请, 参数userId1:{},userId2:{}",userId1,userId2);
        relationshipService.changeStatusByUserId1AndUserId2(userId1,userId2,2);
        return Res.success("已同意");
    }
    @PostMapping("/disagree")
    public Res<String> postDisagree(@RequestBody Relationship relationship, HttpServletRequest request) {
        Integer userId1 = relationship.getUserId1();
        // 20240809安全优化 session->userId
        userId1 = User.getUserIdBySession(userId1,request);
        Integer userId2 = relationship.getUserId2();
        log.info("同意好友申请, 参数userId1:{},userId2:{}",userId1,userId2);
        relationshipService.changeStatusByUserId1AndUserId2(userId1,userId2,0);
        return Res.success("已忽略");
    }
}
