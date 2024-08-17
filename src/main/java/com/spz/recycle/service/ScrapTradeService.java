package com.spz.recycle.service;

import com.spz.public_resource.entity.page.PageBean;
import com.spz.recycle.entity.ScrapTrade;

import java.util.List;

public interface ScrapTradeService {
    PageBean page(Integer page, Integer pageSize, String number, Integer status, String begin, String end);

    String insert(ScrapTrade scrapTrade);

    ScrapTrade getByNumber(ScrapTrade scrapTrade);

    void updateStatus(ScrapTrade scrapTrade);

    List<ScrapTrade> getNumberByUserId(Integer userId);

    ScrapTrade getById(Integer id);

    void updateStatusById(List<Integer> scrapTradeIds,Integer status);
}
