package com.spz.controller;

import com.spz.common.Res;
import com.spz.entity.dto.ScrapTradeDto;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.service.ScrapTradeDetailService;
import com.spz.service.ScrapTradeService;
import com.spz.service.ScrapTypeService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("spz/scrapTrade")
@Slf4j
public class ScrapTradeController {
    @Autowired
    ScrapTradeService scrapTradeService;

    @Autowired
    ScrapTradeDetailService scrapTradeDetailService;

    @GetMapping("/page")
    public Res<PageBean> page(@RequestParam(defaultValue = "1")Integer page,
                              @RequestParam(defaultValue = "10")Integer pageSize,
                              @RequestParam(required = false)String number,
                              @RequestParam(defaultValue = "-1",required = false)Integer status,
                              @RequestParam(required = false) String begin,
                              @RequestParam(required = false) String end) {
        log.info("分页查询中，第{}页，{}条,其他参数：订单编号:{},状态{},开始{},结束{}", page, pageSize, number, status, begin, end);
        PageBean pageBean = scrapTradeService.page(page, pageSize, number,status, begin, end);
        return Res.success(pageBean);
//        return Res.success(new PageBean());
    }

    @PutMapping
    public Res<String> updateStatus(@RequestBody ScrapTrade scrapTrade){
        scrapTradeService.updateStatus(scrapTrade);
        return Res.success("状态修改成功");
    }

    @GetMapping("/{userId}")
    public Res<List<ScrapTrade>> getNumberByUserId(@PathVariable Integer userId){
        List<ScrapTrade> numberByUserId = scrapTradeService.getNumberByUserId(userId);
        return Res.success(numberByUserId);
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
