package com.spz.service.impl;

import com.spz.entity.scrap.ScrapTradeDetail;
import com.spz.mapper.ScrapTradeDetailMapper;
import com.spz.service.ScrapTradeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScrapTradeDetailServiceImpl implements ScrapTradeDetailService {
    @Autowired
    private ScrapTradeDetailMapper scrapTradeDetailMapper;
    @Override
    public void insertList(List<ScrapTradeDetail> scrapTradeDetails, Integer id) {
        scrapTradeDetails.stream().forEach((item) -> {
            //补全信息
            item.setScrapTradeId(id);
            item.setStatus(0); //待确认
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            log.info("{}", item);
            //插入
            scrapTradeDetailMapper.insert(item);
        });
    }
}
