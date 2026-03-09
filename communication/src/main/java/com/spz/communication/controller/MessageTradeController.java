package com.spz.communication.controller;


import com.spz.api.ali.TongYiAiApi;
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

    @Operation(description ="获取 AI 回收价格评估")
    @Parameters({
            @Parameter(name = "itemInfo", description = "物品信息（可选）", required = false)
    })
    @GetMapping("/context")
    public Res<String> getMessageTradeContext(@RequestParam(required = false, defaultValue = "") String itemInfo){
        // 调用 ai 的 api 获取 回收信息内容
        String userContent;
        if (itemInfo == null || itemInfo.trim().isEmpty()) {
            userContent = "请帮我评估一下这个物品的回收价格，需要考虑哪些因素？";
        } else {
            userContent = "请帮我评估以下物品的回收价格：" + itemInfo + "。请给出详细的价格评估和建议。";
        }
        log.info("AI 评估请求：{}", userContent);
        String aiResponse = TongYiAiApi.callWithMessage(userContent);
        log.info("AI 回复：{}", aiResponse);
        return Res.success(aiResponse);
    }

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
//        userId = User.getUserIdByThread(userId);
        return Res.success(messageTradeService.getMessageTradeDtosByUserId(userId));
    }

}
