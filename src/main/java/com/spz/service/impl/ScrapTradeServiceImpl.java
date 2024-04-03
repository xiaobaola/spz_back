package com.spz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.ScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ScrapTradeServiceImpl implements ScrapTradeService {
    @Autowired
    ScrapTradeMapper scrapTradeMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String number, LocalDateTime begin, LocalDateTime end) {
        //1、设置分页参数
        PageHelper.startPage(page,pageSize);

        //2、正常查询
        List<ScrapTrade> scrapTradeList = scrapTradeMapper.list(number, begin, end);

        //为了获取total
        Page<ScrapTrade> scrapTradePage = (Page<ScrapTrade>) scrapTradeList;

        //3、封装pageBean对象
        PageBean pageBean = new PageBean(scrapTradePage.getTotal(), scrapTradePage.getResult());

        return pageBean;
    }

    @Override
    public String insert(ScrapTrade scrapTrade) {
        //1 创建订单编号
        long orderId = IdWorker.getId();
        String number = String.valueOf(orderId);
        //2 补全信息
        scrapTrade.setUpdateTime(LocalDateTime.now());
        scrapTrade.setCreateTime(LocalDateTime.now());
        scrapTrade.setNumber(number);
        // 0待确认
        scrapTrade.setStatus(0);
        log.info("{}", scrapTrade);
        //3 插入数据
        scrapTradeMapper.insert(scrapTrade);
        return number;
    }

    @Override
    public ScrapTrade getByNumber(ScrapTrade scrapTrade) {
        return scrapTradeMapper.getByNumber(scrapTrade);
    }
}
