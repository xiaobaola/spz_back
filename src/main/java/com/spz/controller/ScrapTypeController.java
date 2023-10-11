package com.spz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.common.Res;
import com.spz.entity.scrap.ScrapType;
import com.spz.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/spz/scrapType")
@RestController
@Slf4j
public class ScrapTypeController {
    @Autowired
    private ScrapTypeService scrapTypeService;

    @GetMapping("/page")
    public Res<Page<ScrapType> > page(Integer page, Integer pageSize, String name) {
        log.info("废品类型分页查询： page:{}, pageSize:{}", page, pageSize);
        //构造分页构造器
        Page<ScrapType> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<ScrapType> queryWrapper = new LambdaQueryWrapper<>();
        //添加一个条件
        queryWrapper.like(StringUtils.isNotEmpty(name), ScrapType::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(ScrapType::getUpdateTime);

        //执行查询 SM层封装 省代码
        scrapTypeService.page(pageInfo, queryWrapper);

        return Res.success(pageInfo);
//        log.info("收到list的get请求");
//        ArrayList<ScrapType> list = new ArrayList<>();
//        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.notifyAll();
//        list = (ArrayList<ScrapType>) scrapTypeService.list(queryWrapper);
//        return Res.success(list);
    }
    @GetMapping("/test")
    public Res<ScrapType> test(){
        log.info("测试中");
        ScrapType scrapType = scrapTypeService.getById(1);
        return Res.success(scrapType);
    }
    @GetMapping("/login")
    public Res<String> login(){
        log.info("接收到登录请求");
        return Res.success("登录成功");
    }
}
