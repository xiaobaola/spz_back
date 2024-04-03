package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.dto.ScrapTradeDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.service.ScrapTradeDetailService;
import com.spz.service.ScrapTradeService;
import com.spz.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("spz/scrapTrade")
@Slf4j
public class ScrapTradeController {
    @Autowired
    ScrapTradeService scrapTradeService;

    @Autowired
    ScrapTradeDetailService scrapTradeDetailService;

    @GetMapping("/page")
    public Res<PageBean> page(@PathVariable @RequestParam(defaultValue = "1")Integer page,
                              @PathVariable @RequestParam(defaultValue = "10")Integer pageSize,
                              @PathVariable @RequestParam(defaultValue = "null")String number,
                              @PathVariable @RequestParam(defaultValue = "5")Integer statusType,
                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime begin,
                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end) {
        log.info("分页查询中，第{}页，{}条,其他参数：{},{},{},{}", page, pageSize, number, statusType, begin, end);
        PageBean pageBean = scrapTradeService.page(page, pageSize, number, begin, end);
        return Res.success(pageBean);
    }

    @PostMapping
    public Res<String> cart(@RequestBody ScrapTradeDto scrapTradeDto) {
        log.info("账单上传: {}",scrapTradeDto);
        ScrapTrade scrapTrade = (ScrapTrade) scrapTradeDto;
        //插入总账单并返回订单编号
        String number = scrapTradeService.insert(scrapTrade);
        ScrapTrade scrapTradeId = scrapTradeService.getByNumber(scrapTrade);
        //批量插入订单细节信息
        scrapTradeDetailService.insertList(scrapTradeDto.getScrapTradeDetails(), scrapTradeId.getId());
        return Res.success("上传记录成功");
    }
}
