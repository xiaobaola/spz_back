package com.spz.personal.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spz.common.Res;
import com.spz.personal.entity.UserItemCollect;
import com.spz.personal.service.UserItemCollectService;
import com.spz.secondHand.entity.SecondHandItem;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/spz/user/collect")
@RestController
@Slf4j
@Tag(name = "用户收藏物品模块")
@RequiredArgsConstructor
public class UserItemCollectController {

    private final UserItemCollectService collectService;

    @GetMapping("/list")
    public Res<List<SecondHandItem>> list(@RequestParam int userId) {
        log.info("用户查询浏览物品记录，参数{}", userId);
        return Res.success(collectService.listByUserId(userId));
    }

    @GetMapping
    public Res<Integer> getCollectByUserIdAndItemId(@RequestParam int userId, @RequestParam int itemId) {
        log.info("获取单个收藏物品，参数{}", userId);
        return Res.success(collectService.hasOneByUserIdAndItemId(userId, itemId));
    }

    @PostMapping
    public Res<String> add(@RequestBody UserItemCollect collect) {
        log.info("用户收藏物品，参数{}", collect);
        // 后期优化 如果已经有数据了 直接返回
        collectService.save(collect);
        return Res.success("收藏成功");
    }

    @DeleteMapping
    public Res<String> delete(@RequestParam int userId, @RequestParam int itemId) {
        log.info("用户取消收藏，参数{}", userId);
        // 后期收藏可以用一个isDelete来判断 减少数据库修改次数 定时删除
        LambdaQueryWrapper<UserItemCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserItemCollect::getUserId, userId);
        queryWrapper.eq(UserItemCollect::getItemId, itemId);
        collectService.remove(queryWrapper);
        return Res.success("取消收藏成功");
    }
}
