package com.spz.recycle.service;

import com.spz.entity.page.PageBean;
import com.spz.recycle.entity.ScrapTrade;

import java.util.List;

public interface ScrapTradeService {
    PageBean page(Integer page, Integer pageSize, String number, Integer status, String begin, String end);

    String add(ScrapTrade scrapTrade);

    ScrapTrade getByNumber(ScrapTrade scrapTrade);

    void changeStatus(ScrapTrade scrapTrade);

    List<ScrapTrade> getNumberByUserId(Integer userId);

    ScrapTrade getById(Integer id);

    void changeStatusById(List<Integer> scrapTradeIds, Integer status);

    List<Integer> getIdsByUserId(Integer userId);

    List<ScrapTrade> getListByUserId(Integer userId);
}
