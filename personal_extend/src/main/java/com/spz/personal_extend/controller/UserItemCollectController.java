package com.spz.personal_extend.controller;

import com.spz.common.Res;
import com.spz.personal_extend.service.UserItemCollectService;
import com.spz.secondHand.entity.SecondHandItem;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
