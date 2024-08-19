package com.spz.recycle.service.impl;

import com.spz.recycle.entity.ScrapTradeDetail;
import com.spz.recycle.mapper.ScrapTradeDetailMapper;
import com.spz.recycle.service.ScrapTradeDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ScrapTradeDetailServiceImpl implements ScrapTradeDetailService {

    private ScrapTradeDetailMapper scrapTradeDetailMapper;

    @Autowired
    public void setScrapTradeDetailMapper(ScrapTradeDetailMapper scrapTradeDetailMapper) {
        this.scrapTradeDetailMapper = scrapTradeDetailMapper;
    }

    @Override
    public void addList(List<ScrapTradeDetail> scrapTradeDetails, Integer id) {
        for (ScrapTradeDetail item : scrapTradeDetails) {//补全信息
            item.setScrapTradeId(id);
            item.setStatus(0); //待确认
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            log.info("{}", item);
            //插入
            scrapTradeDetailMapper.insert(item);
        }
    }
}
