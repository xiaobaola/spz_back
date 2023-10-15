package com.spz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.common.Res;
import com.spz.entity.scrap.ScrapType;
import com.spz.entity.user.UserMessage;
import com.spz.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/spz/scrapType")
@RestController
@Slf4j
public class ScrapTypeController {
    @Autowired
    private ScrapTypeService scrapTypeService;

    /**
     * @param
     * @return @return {@link Res }<{@link ArrayList }<{@link ScrapType }>>
     * @author last
     * @describe
     */
    @GetMapping("/list")
    public Res<ArrayList<ScrapType>> list2() {
        log.info("get 废品类型列表");
        ArrayList<ScrapType> scrapTypeArrayList = scrapTypeService.list2();
        return Res.success(scrapTypeArrayList);
    }

    @PutMapping
    public Res<String> updateById(@RequestBody ScrapType scrapType) {
        log.info("修改: {}", scrapType);
        scrapTypeService.updateById(scrapType);
        return Res.success("修改废品类型成功");
    }
//    @GetMapping("/test")
//    public Res<ScrapType> test(){
//        scrapTypeService.insert2();
//        log.info("测试中");
//        ScrapType scrapType = new ScrapType();
//        return Res.success(scrapType);
//    }
//    @GetMapping("/login")
//    public Res<String> login(){
//        log.info("接收到登录请求");
////        return Res.success("登录成功");
//        return Res.error("错误");
//    }
}
