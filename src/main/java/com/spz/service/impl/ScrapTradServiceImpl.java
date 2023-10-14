package com.spz.service.impl;

import com.spz.mapper.ScrapTradMapper;
import com.spz.service.ScrapTradService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapTradServiceImpl implements ScrapTradService {
    @Autowired
    ScrapTradMapper scrapTradMapper;
}
