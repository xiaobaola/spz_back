package com.spz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spz.common.Res;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.Scrap;
import com.spz.service.ScrapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("spz/scrap")
@Slf4j
public class ScrapController {
    @Autowired
    private ScrapService scrapService;

    @GetMapping("/{id}/list")
    public Res<ArrayList<Scrap>> list(@PathVariable Integer id) {
        log.info("查询: id:{}", id);
        ArrayList<Scrap> list = scrapService.listByTypeId(id);
        return Res.success(list);
    }
    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                 String name,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{},{}", page, pageSize, name, begin, end);
        PageBean pageBean = scrapService.page(page, pageSize, name, begin, end);
        return Res.success(pageBean);
    }
}
