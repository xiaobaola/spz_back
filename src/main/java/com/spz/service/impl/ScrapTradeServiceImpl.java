package com.spz.service.impl;

import com.spz.mapper.ScrapTradeMapper;
import com.spz.service.ScrapTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapTradeServiceImpl implements ScrapTradeService {
    @Autowired
    ScrapTradeMapper scrapTradeMapper;

}
