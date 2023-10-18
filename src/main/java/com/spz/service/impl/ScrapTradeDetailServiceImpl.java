package com.spz.service.impl;

import com.spz.entity.scrap.ScrapTradeDetail;
import com.spz.mapper.ScrapTradeDetailMapper;
import com.spz.service.ScrapTradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScrapTradeDetailServiceImpl implements ScrapTradeDetailService {
    @Autowired
    private ScrapTradeDetailMapper scrapTradeDetailMapper;
    @Override
    public void insertList(List<ScrapTradeDetail> scrapTradeDetails, String number) {
        //补全信息
        scrapTradeDetails = scrapTradeDetails.stream().map((item) -> {
//            item.setScrapTradeId();
            return item;
        }).collect(Collectors.toList());
    }
}
