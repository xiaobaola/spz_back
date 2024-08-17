package com.spz.recycle.controller;

import com.spz.public_resource.common.Res;
import com.spz.recycle.entity.dto.ScrapTradeDto;
import com.spz.public_resource.entity.page.PageBean;
import com.spz.recycle.entity.ScrapTrade;
import com.spz.recycle.entity.wrapper.ScrapWrapper;
import com.spz.recycle.service.ScrapTradeDetailService;
import com.spz.recycle.service.ScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//        if(scrapTrade.getStatus() == 3) {
//            return Res.error("订单已完成不能取消");
//        }
        scrapTradeService.updateStatus(scrapTrade);
        return Res.success("状态修改成功");
    }

    @PutMapping("/ids")
    public Res<String> updateStatusById(@RequestBody ScrapWrapper wrapper){
        List<Integer> scrapTradeIds = wrapper.getScrapTradeIds();
        Integer status = wrapper.getStatus();
        scrapTradeService.updateStatusById(scrapTradeIds,status);
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
