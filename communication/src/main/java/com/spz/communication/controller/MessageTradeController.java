package com.spz.communication.controller;


import com.spz.public_resouce.common.Res;
import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.dto.MessageTradeDto;
import com.spz.personal.entity.User;
import com.spz.communication.service.MessageTradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spz/messageTrade")
@Slf4j
public class MessageTradeController {

    private MessageTradeService messageTradeService;
    @Autowired
    public void setMessageTradeService(MessageTradeService messageTradeService) {
        this.messageTradeService = messageTradeService;
    }

    @PostMapping
    public Res<String> createMessageTrade(@RequestBody MessageTrade messageTrade){
        log.info("新增: {}", messageTrade);
        messageTradeService.add(messageTrade);
        return Res.success("新增信息成功");
    }


    /**
     * Author last
     * Param
     * Return @return {@link Res }<{@link List }<{@link MessageTrade }>>
     * Describe 管理员获取所有消息交易
     */
    @GetMapping("/list")
    public Res<List<MessageTrade>> managerGetAllMessageTrade(){
        // 可以做缓存，如果服务器资源充足的情况下，访问的数据不易改变，少了管理员下，可以设置过期时间5-10min，多则15-30min
        log.info("get 信息列表");
        return Res.success(messageTradeService.getList());
    }

    @GetMapping("/{id}")
    public Res<MessageTrade> selectMessageTradeById(@PathVariable Integer id){
        // 获取单个订单信息不需要做缓存 访问不频繁，访问内容不同，优先完成访问量多的缓存
        log.info("get 信息列表 id:{}",id);
        return Res.success(messageTradeService.getById(id));
    }

    @GetMapping()
    public Res<List<MessageTradeDto>> getAllByMessageTradeId(@RequestParam Integer userId, HttpServletRequest request){
        // 可以考虑做缓存 同一用户在一个时间段内，可能会频繁访问，可以设置过期时间5-10min
        log.info("get 信息列表 userId:{}",userId);
        // 20240809安全优化userId
        userId = User.getUserIdBySession(userId,request);
        return Res.success(messageTradeService.getMessageTradeDtosByUserId(userId));
    }

}
