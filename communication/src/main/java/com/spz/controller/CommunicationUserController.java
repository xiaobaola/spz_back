package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.dto.MessageUserDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.relationship.Relationship;
import com.spz.entity.User;
import com.spz.service.CommunicationUserService;
import com.spz.service.MessageTradeService;
import com.spz.service.RelationshipService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/spz/user")
@RestController
@Slf4j
public class CommunicationUserController {
    @Autowired
    private CommunicationUserService communicationUserService;

    @Autowired
    private MessageTradeService messageTradeService;

    @Autowired
    private RelationshipService relationshipService;


    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              String username,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{}", page, pageSize, username, begin, end);
        PageBean pageBean = communicationUserService.page(page, pageSize, username, begin, end);
        return Res.success(pageBean);
    }

    //获取好友列表 采用session获取userId,方法封装在user中
    @GetMapping("/friend")
    public Res<List<User>> getUserByUserId(@RequestParam Integer userId,HttpServletRequest request){
        // 20240808安全优化 session->userid
        userId = User.getUserIdBySession(userId,request);
        log.info("get 好友列表, userId:{}",userId);
        return Res.success(messageTradeService.getUsersByUserId(userId));
    }

    @PostMapping("/friend/add")
    public Res<String> addRelationship(@RequestBody Relationship relationship,HttpServletRequest request) {
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
    public Res<List<MessageUserDto>> getUserDtoListByInfo(@RequestParam String info, @RequestParam Integer userId, HttpServletRequest request){
        // 20240809安全优化 session->userId
        userId = User.getUserIdBySession(userId,request);
        log.info("用户搜索, 参数:{},{}",info,userId);
        List<MessageUserDto> messageUserDtoListByInfo = communicationUserService.getUserDtoListByInfo(info, userId);
        log.info("返回,{}", messageUserDtoListByInfo);
        return Res.success(messageUserDtoListByInfo);
    }

    @GetMapping("/newFriendList")
    public Res<List<MessageUserDto>> getUserDtoListByUserId(@RequestParam Integer userId, HttpServletRequest request) {
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
