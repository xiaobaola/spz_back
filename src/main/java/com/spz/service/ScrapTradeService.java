package com.spz.service;

import com.spz.entity.page.PageBean;

import java.time.LocalDate;

public interface ScrapTradeService {
    PageBean page(Integer page, Integer pageSize, String number, LocalDate begin, LocalDate end);
}
