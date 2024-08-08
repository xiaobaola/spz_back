package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.dto.UserDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.relationship.Relationship;
import com.spz.entity.safe.Token;
import com.spz.entity.user.User;
import com.spz.entity.wrapper.Wrapper;
import com.spz.service.MessageTradeService;
import com.spz.service.RelationshipService;
import com.spz.service.SafeService;
import com.spz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/spz/user")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageTradeService messageTradeService;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private SafeService safeService;

    @GetMapping
    public Res<User> getById(User user) {
        log.info("get：用户id{}", user.getId());
        user = userService.getById(user);
        return Res.success(user);
    }
    @PostMapping("/login")
    //HttpServletRequest request,
    public Res<User> login(@RequestBody User userMessage, HttpServletRequest request) {
        String username = userMessage.getUsername();
        String password = userMessage.getPassword();
        log.info("login请求 username:{}, password{}", username, password);
        User user = userService.getByUsernameAndPassword(userMessage);
        if(user != null) {
            log.info("user = {}", user);
            // session
            HttpSession session = request.getSession();
            // 向session设置值
            session.setAttribute("user",user);
            // token
//            String token = user.getId().toString();
//            request.getSession().setAttribute("token", token);
//            // 没有才插入
//            Token getToken = safeService.getByToken(token);
//            if(getToken == null) {
//                // 存储token
//                Token t = new Token(token);
//                safeService.setToken(t);
//            }
            return Res.success(user);
        }
        return Res.error("用户名或密码错误");
    }

    @PostMapping("/logout")
    //HttpServletRequest request,
    public Res<String> logout(@RequestBody User user) {
        // 清理session中保存的管理员id
//        request.getSession().removeAttribute("user");
        return Res.success("退出成功");
    }

    @PutMapping
    public Res<String> updateById(@RequestBody User user) {
        log.info("userMessage{}", user);
        userService.updeteById(user);
        return Res.success("用户更新成功");
    }

    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              String username,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{},{}", page, pageSize, username, begin, end);
        PageBean pageBean = userService.page(page, pageSize, username, begin, end);
        return Res.success(pageBean);
    }

    @GetMapping("/{id}")
    public Res<User> getByIdNumber(@PathVariable Integer id) {
        log.info("查询: id: {}", id);
        User user = userService.getByIdNumber(id);
        return Res.success(user);
    }

    @PostMapping
    public Res<String> insert(@RequestBody User user) {
        log.info("新增: {}", user);
        userService.insert(user);
        return Res.success("新增用户成功");
    }

    //原来
//    @GetMapping("/friend")
//    public Res<List<User>> getUserByUserId(@RequestParam Integer userId){
//        log.info("get 好友列表");
//        return Res.success(messageTradeService.getUserMessage(userId));
//    }
    //现在
    // 获取好友列表 采用sesion获取userId
    @GetMapping("/friend")
    public Res<List<User>> getUserByUserId(HttpServletRequest request){
        log.info("get 好友列表");
        User user = (User) request.getSession().getAttribute("user");
        return Res.success(messageTradeService.getUserMessage(user.getId()));
    }

    @PostMapping("/friend/add")
    public Res<String> addRelationship(@RequestBody Relationship relationship) {
        log.info("添加好友申请, 参数{}", relationship);
        relationshipService.addRelationship(relationship);
//        return Res.error("发送申请失败");
        return Res.success("发送申请成功");
    }

    @GetMapping("/search")
    public Res<List<UserDto>> getUserDtoListByInfo(@RequestParam String info, @RequestParam Integer userId){
        log.info("用户搜索, 参数:{},{}",info,userId);
        List<UserDto> userDtoListByInfo = userService.getUserDtoListByInfo(info, userId);
        log.info("返回,{}",userDtoListByInfo);
        return Res.success(userDtoListByInfo);
    }

    @GetMapping("/newFriendList")
    public Res<List<UserDto>> getUserDtoListByUserId(@RequestParam Integer userId) {
        log.info("查询好友验证, 参数{}", userId);
        return Res.success(userService.getUserDtoListByUserId(userId));
    }
    @PostMapping("/agree")
    public Res<String> postAgree(@RequestBody Relationship relationship) {
        Integer userId1 = relationship.getUserId1();
        Integer userId2 = relationship.getUserId2();
        log.info("同意好友申请, 参数userId1:{},userId2:{}",userId1,userId2);
        relationshipService.changeStatusByUserId1AndUserId2(userId1,userId2,2);
        return Res.success("已同意");
    }
    @PostMapping("/disagree")
    public Res<String> postDisagree(@RequestBody Relationship relationship) {
        Integer userId1 = relationship.getUserId1();
        Integer userId2 = relationship.getUserId2();
        log.info("同意好友申请, 参数userId1:{},userId2:{}",userId1,userId2);
        relationshipService.changeStatusByUserId1AndUserId2(userId1,userId2,0);
        return Res.success("已忽略");
    }
}
