package com.spz.recycle.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spz.public_resource.entity.page.PageBean;
import com.spz.recycle.entity.ScrapTrade;
import com.spz.recycle.mapper.ScrapTradeMapper;
import com.spz.recycle.service.ScrapTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ScrapTradeServiceImpl implements ScrapTradeService {
    @Autowired
    ScrapTradeMapper scrapTradeMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String number, Integer status, String begin, String end) {

        try {
            // 定义输出格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date beginDate = null;
            Date endDate = null;
            // 将字符串转化为日期
            if(begin != null && !begin.isEmpty() && !"null".equals(begin)) {
                beginDate = sdf.parse(begin);
            }
            if(end != null && !end.isEmpty() && !"null".equals(end)) {
                endDate = sdf.parse(end);
            }

            log.info("开始时间: {}, 结束时间: {}", beginDate, endDate);
            //1、设置分页参数
            PageHelper.startPage(page,pageSize);

            //2、正常查询
            List<ScrapTrade> scrapTradeList = scrapTradeMapper.list(number, status, beginDate, endDate);

            //为了获取total
            Page<ScrapTrade> scrapTradePage = (Page<ScrapTrade>) scrapTradeList;

            //3、封装pageBean对象
            PageBean pageBean = new PageBean(scrapTradePage.getTotal(), scrapTradePage.getResult());

            return pageBean;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String add(ScrapTrade scrapTrade) {
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

    @Override
    public void changeStatus(ScrapTrade scrapTrade) {
        scrapTradeMapper.updateStatus(scrapTrade);
    }

    @Override
    public List<ScrapTrade> getNumberByUserId(Integer userId) {
        List<ScrapTrade> scrapTrades = scrapTradeMapper.selectListByUserId(userId);
        scrapTrades.sort(Comparator.comparing(ScrapTrade::getCreateTime).reversed());
        return scrapTrades;
    }

    @Override
    public ScrapTrade getById(Integer id) {
        return scrapTradeMapper.getById(id);
    }

    @Override
    public void changeStatusById(List<Integer> scrapTradeIds, Integer status) {
        List<ScrapTrade> list = new ArrayList<>();
        for (Integer scrapTradeId:scrapTradeIds){
            list.add(scrapTradeMapper.getById(scrapTradeId));
        }
        for (ScrapTrade element:list){
            if(element.getStatus() == 2){
                scrapTradeMapper.updateStatusById(element.getId());
            }
        }
    }

    @Override
    public List<Integer> getIdsByUserId(Integer userId) {
        return scrapTradeMapper.selectIdsByUserId(userId);
    }

    @Override
    public List<ScrapTrade> getListByUserId(Integer userId) {
        return scrapTradeMapper.selectListByUserId(userId);
    }


}
