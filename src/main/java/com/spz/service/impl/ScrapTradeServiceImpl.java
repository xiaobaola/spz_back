package com.spz.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.entity.manager.Manager;
import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;
import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.ScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScrapTradeServiceImpl implements ScrapTradeService {
    @Autowired
    ScrapTradeMapper scrapTradeMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String number, LocalDate begin, LocalDate end) {
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
}
