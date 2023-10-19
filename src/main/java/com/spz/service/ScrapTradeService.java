package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;

import java.time.LocalDate;

public interface ScrapTradeService {
    PageBean page(Integer page, Integer pageSize, String number, LocalDate begin, LocalDate end);

    String insert(ScrapTrade scrapTrade);

    ScrapTrade getByNumber(ScrapTrade scrapTrade);
}
