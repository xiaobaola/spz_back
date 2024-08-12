package com.spz.controller;

import com.spz.entity.secondhand.SecondHandItem;
import com.spz.service.SecondHandItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spz/secondHand/item")
@Slf4j
public class SecondHandItemController {
    @Autowired
    private SecondHandItemService secondHandItemService;

    @GetMapping("/list")
    public List<SecondHandItem> listStatus2(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return secondHandItemService.getSomeByStatus(2);
    }
    @GetMapping("/list/manager")
    public List<SecondHandItem> listStatus1(){
        //返回所有二手物品信息 1:待审核 2:发布中 3:下架
        //可以优化 pageHelper 分页查询
        log.info("获取所有二手物品信息");
        return secondHandItemService.getSomeByStatus(1);
    }
}
