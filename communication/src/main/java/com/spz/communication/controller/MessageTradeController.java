package com.spz.communication.controller;


import com.spz.common.Res;
import com.spz.communication.entity.message.MessageTrade;
import com.spz.communication.entity.dto.MessageTradeDto;
import com.spz.personal.entity.User;
import com.spz.communication.service.MessageTradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name ="交易信息管理")
@RestController
@RequestMapping("/spz/messageTrade")
@Slf4j
@RequiredArgsConstructor
public class MessageTradeController {

    // 单例
    private final MessageTradeService messageTradeService;

    @Operation(description ="新增信息")
    @Parameters({
            @Parameter(name = "messageTrade", description = "信息", required = true)
    })
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
    @Operation(description ="管理员获取所有消息交易")
    @GetMapping("/list")
    public Res<List<MessageTrade>> managerGetAllMessageTrade(){
        // 可以做缓存，如果服务器资源充足的情况下，访问的数据不易改变，少了管理员下，可以设置过期时间5-10min，多则15-30min
        log.info("get 信息列表");
        return Res.success(messageTradeService.getList());
    }

    @Operation(description = "获取单个信息")
    @Parameters({
            @Parameter(name = "id", description = "信息id", required = true)
    })
    @GetMapping("/{id}")
    public Res<MessageTrade> selectMessageTradeById(@PathVariable Integer id){
        // 获取单个订单信息不需要做缓存 访问不频繁，访问内容不同，优先完成访问量多的缓存
        log.info("get 信息列表 id:{}",id);
        return Res.success(messageTradeService.getById(id));
    }

    @Operation(description ="用户获取回收交易信息")
    @Parameters({
            @Parameter(name = "userId", description = "用户id", required = true)
    })
    @GetMapping()
    public Res<List<MessageTradeDto>> getAllByMessageTradeId(@RequestParam Integer userId){
        // 可以考虑做缓存 同一用户在一个时间段内，可能会频繁访问，可以设置过期时间5-10min
        log.info("get 信息列表 userId:{}",userId);
        // 20240809安全优化userId
        userId = User.getUserIdByThread(userId);
        return Res.success(messageTradeService.getMessageTradeDtosByUserId(userId));
    }

}
