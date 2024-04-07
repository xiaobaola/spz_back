package com.spz.service;

import com.spz.entity.page.PageBean;
import com.spz.entity.scrap.ScrapTrade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScrapTradeService {
    PageBean page(Integer page, Integer pageSize, String number, LocalDateTime begin, LocalDateTime end);

    String insert(ScrapTrade scrapTrade);

    ScrapTrade getByNumber(ScrapTrade scrapTrade);

    void updateStatus(ScrapTrade scrapTrade);

    List<ScrapTrade> getNumberByUserId(Integer userId);
}
